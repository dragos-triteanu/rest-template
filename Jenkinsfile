pipeline{
agent any
options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
tools {
    jdk 'Java9'
    gradle 'Gradle4.5'
}
paramters{
    string (name: 'IMAGE_REPO_NAME', defaultValue: 'resttemplate/rest-template', description:'')
    string (name: 'LATEST_BUILD_TAG', defaultValue: 'latest', description:'')
  }
stages{
  stage('Gradle Build'){
      steps {
          sh "chmod a+x gradlew"
          sh "./gradlew clean"
          sh "./gradlew build"
      }
  }
  stage('Docker Build'){
      enviroment {
          COMMIT_TAG = sh(returnStdout: true, script: 'git rev-parse HEAD').trim().take(7)
      }
      steps{
        sh "./gradlew docker"
        sh "docker tag ${params.IMAGE_REPO_NAME} ${params.IMAGE_REPO_NAME}:$COMMIT_TAG"
      }
  }
}

