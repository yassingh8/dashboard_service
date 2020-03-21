pipeline {
//    {dockerfile true} 
  agent any
  tools {
    maven 'maven3';
}
 
  stages {
    stage('Unit Test') {
       steps {
           sh label: 'Test running', script: '''mvn test'''
           echo 'Hello Testing done'
       }
       }
    stage('Jacoco Coverage Report') {
       steps{
        jacoco()
        }
    }   
      stage('SonarQube'){
        steps{
            sh label: '', script: '''mvn sonar:sonar \
          -Dsonar.host.url=http://sonar:9000 \
          -Dsonar.login=f18bd60881e930515051e739c9850bc14c324476'''
        }
    }
    stage('Maven Build'){
        steps{
                sh label:'Maven Build of war file', script:'''
                    mvn clean install -DskipTests=false
                    mvn package
                '''
        }
    }
  stage('Docker Image Build Push') {
        steps{
           sh '''docker build -t 10.0.1.11:5000/dashboard_service:latest .
                 docker push 10.0.1.11:5000/dashboard_service
                 docker rmi 10.0.1.11:5000/dashboard_service
              '''
        }
      }
  }
 
}
