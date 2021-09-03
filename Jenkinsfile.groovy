pipeline {
    agent any
    environment {
		GIT_CREDENTIALS = credentials ('git-credentials')
        DOCKER_CREDENTIALS = 'docker-credentials'
        image = "kevinpuentesendava/movie-analyst-ui" + ":$BUILD_NUMBER"
        dockerImage = ''
        dockerContainer=''
	}
 
    stages {
        stage('Clone git repo') {
            steps {
                echo 'Cloning git repository'
                git credentialsId: 'git-credentials', url: 'git@github.com:KevinEndava/movie-analyst-ui.git'
            }
        }
        stage('Build docker image'){
            steps{
                echo 'Building docker image'
                script{
                    dockerImage= docker.build image
                }
            }
        }
        stage('Push the image') { 
            steps { 
                echo 'Pushing image'
                script { 
                    docker.withRegistry( '', DOCKER_CREDENTIALS) { 
                        dockerImage.push() 
                    } 
                } 
            }
        }
        stage("Run the docker container and remove docker image"){
            steps{
                echo 'Runing the container'
                script{
                    dockerContainer = dockerImage.run('-p 3030:3030 --name server')
                }
            }
        } 
    }
}