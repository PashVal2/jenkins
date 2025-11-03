pipeline {
    agent any

    environment {
        IMAGE_NAME = "spring-app"
        IMAGE_TAG = "latest"
        STAGING_CONTAINER = "spring-staging"
        PROD_CONTAINER = "spring-prod"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/PashVal2/jenkins.git'
            }
        }

        stage('Build JAR') {
            agent {
                docker {
                    image 'maven:3.8.5-openjdk-17-slim'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Test Docker Container') {
            steps {
                sh """
                docker run -d --name test-app -p 8081:8081 ${IMAGE_NAME}:${IMAGE_TAG}
                sleep 10
                # Проверяем HTTP через host.docker.internal
                curl http://host.docker.internal:8081 || (echo 'Container test failed!' && exit 1)
                docker stop test-app && docker rm test-app
                """
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh """
                docker stop ${STAGING_CONTAINER} || true
                docker rm ${STAGING_CONTAINER} || true
                docker run -d --name ${STAGING_CONTAINER} -p 8082:8081 ${IMAGE_NAME}:${IMAGE_TAG}
                sleep 10
                curl -f http://host.docker.internal:8082 || (echo 'Staging test failed!' && exit 1)
                """
            }
        }

        stage('Manual Approval') {
            steps {
                input message: 'Deploy to Production?', ok: 'Deploy'
            }
        }

        stage('Deploy to Production') {
            steps {
                sh """
                docker stop ${PROD_CONTAINER} || true
                docker rm ${PROD_CONTAINER} || true
                docker run -d --name ${PROD_CONTAINER} -p 8083:8081 ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }
    }
}