pipeline {
    agent any

    environment {
        IMAGE_NAME = "spring-app"
        IMAGE_TAG = "latest"
        PROD_CONTAINER = "jenkins-app"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/PashVal2/jenkins.git'
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