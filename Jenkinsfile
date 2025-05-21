pipeline {
    agent any

    stages {
        stage('Compilar') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Construir imagen Docker') {
            steps {
                sh 'docker build -t api-musica .'
            }
        }

        stage('Levantar contenedores') {
            steps {
                sh 'docker-compose up -d --build'
            }
        }
    }
}
