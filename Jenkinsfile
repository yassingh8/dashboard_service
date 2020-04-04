pipeline {
//    {dockerfile true} 
  agent any
  tools {
    maven 'maven3';
}

  stages {
//     stage('Unit Test') {
//        steps {
//            sh label: 'Test running', script: '''mvn test'''
//            echo 'Hello Testing done'
//        }
//        }
//     stage('Jacoco Coverage Report') {
//        steps{
//         jacoco()
//         }
//     }  
//     stage('SonarQube'){
//        steps{
//            sh label: '', script: '''mvn sonar:sonar \
//           -Dsonar.projectKey=PSI-2020 \
//           -Dsonar.host.url=https://tools.publicis.sapient.com/sonar \
//           -Dsonar.login=7febead52196525a597e49d8a215156ddbb5ef77 \
//           -Dsonar.projectName=dashboard-service \
// 		  -Dsonar.projectVersion=0.0.1 \
// 		  -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco-ut/jacoco.xml \
// 		  '''
//        }
//    }
    stage('Maven Build'){
        steps{
                sh label:'Maven Build of jar file', script:'''
                    mvn clean package -DskipTests
                '''
        }
    }
  stage('Docker Image Build') {
      steps{
         sh "docker build -t dashboard_service ."
      }
    }
    stage('Docker push to registry'){
        steps{
            sh "docker image tag dashboard_service 10.0.1.175:5000/dashboard_service"
            sh "docker push 10.0.1.175:5000/dashboard_service"
        }
    }
    stage('Remove images from jenkins instance'){
        steps{
            sh "docker rmi 10.0.1.175:5000/dashboard_service"
            sh "docker rmi dashboard_service"

        }
    }
    stage('Pull from registry')
    {
        steps{
                sshagent(credentials:['token_key']){
                        sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.108 'sudo docker pull 10.0.1.175:5000/dashboard_service'"
        }
        }
    }
    
    stage('Removing the previous container')
    {
        steps{
                sshagent(credentials:['token_key']){
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.108  'sudo docker stop dashboard && sudo docker rm dashboard;bash -l'"
                }
        }
    }
    
    stage('Run docker image')
    {
        steps{
            sshagent(credentials:['token_key']){
                  sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.108  'sudo docker run -d --name dashboard 10.0.1.175:5000/dashboard_service;bash -l'"
            }
        }
    }
    stage('Tag docker image without the registry address')
    {
        steps{
            sshagent(credentials:['token_key']){
              //    sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.108  'sudo docker image tag 10.0.1.175:5000/dashboard_service dashboard_service'"
                   sh "ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.108  'sudo docker image prune -f'"
            }
        }
    }
  

  }

}
