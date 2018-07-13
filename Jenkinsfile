pipeline {
    agent {
        node {
            label 'docker'
        }
    }
	tools { 
       jdk 'JAVA_HOME' 
    	 }	   
    stages { 	
        stage('Build Jar') {
            steps {
                bat "mvn clean"
		   
		    
			     
            }
        }
        stage('Build Image') {
            steps {
                 script {
                    app = docker.build("vinsdocker/containertest")
		 }
            }
        }
        stage('Push Image') {
            steps {
                script {
			 docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
			        	app.push("${BUILD_NUMBER}")
			            app.push("latest")
			       
			        }
                }
            }
        }        
    }
}
