pipeline {
    agent any

    stages {
        stage('Clonar código') {
            steps {
                git 'https://github.com/AxelLuna04/API_MusAPI.git'
            }
        }

        stage('Compilar y construir imagen Docker') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Levantar contenedores') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }
}
