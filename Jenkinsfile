pipeline {
    agent any

    environment {
        IMAGE_NAME = "spring-app"
        IMAGE_TAG = "latest"
        STAGING_CONTAINER = "spring-staging"
        PROD_CONTAINER = "spring-prod"
    }

    stages {
        // Получаем код из Git
        stage('Checkout') {
            steps {
                echo "Checking out code from Git..."
                git 'https://github.com/PashVal2/jenkins.git'
            }
        }

        // Сборка JAR и запуск тестов
        stage('Build JAR & Run Tests') {
            steps {
                echo "Building project and running tests..."
                sh 'mvn clean package'
            }
            post {
                always {
                    echo "Recording test results..."
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // Сборка Docker образа
        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        // Тестируем Docker контейнер
        stage('Test Docker Container') {
            steps {
                echo "Testing Docker container..."
                sh """
                docker run -d --name test-app -p 8081:8081 ${IMAGE_NAME}:${IMAGE_TAG}
                sleep 10
                curl -f http://localhost:8081 || (echo "Container test failed!" && exit 1)
                docker stop test-app && docker rm test-app
                """
            }
        }

        // Деплой на стейджинг
        stage('Deploy to Staging') {
            steps {
                echo "Deploying to staging..."
                sh """
                docker stop ${STAGING_CONTAINER} || true
                docker rm ${STAGING_CONTAINER} || true
                docker run -d --name ${STAGING_CONTAINER} -p 8082:8081 ${IMAGE_NAME}:${IMAGE_TAG}
                sleep 5
                curl -f http://localhost:8082 || (echo "Staging test failed!" && exit 1)
                """
            }
        }

        // Ручное подтверждение деплоя в продакшн
        stage('Manual Approval') {
            steps {
                input message: 'Deploy to Production?', ok: 'Deploy'
            }
        }

        // Деплой в продакшн
        stage('Deploy to Production') {
            steps {
                echo "Deploying to production..."
                sh """
                docker stop ${PROD_CONTAINER} || true
                docker rm ${PROD_CONTAINER} || true
                docker run -d --name ${PROD_CONTAINER} -p 8083:8081 ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
