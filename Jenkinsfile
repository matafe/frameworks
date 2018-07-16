pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean -DskipTests install'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
            	sh 'echo "Deploying the app"'
            }
        }        
    }
}