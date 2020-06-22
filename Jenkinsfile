

pipeline {
  agent  { label 'master' }

  environment {
    VERSION="0.1"
    NL_API_TOKEN="${env.NL_WEB_API_KEY}"
        ZONE="${env.nlwebZONE}"
    DOCKER_COMPOSE_TEMPLATE="$WORKSPACE/neoload/infrastructure/docker-compose.template"
    DOCKER_COMPOSE_LG_FILE = "$WORKSPACE/neoload/infrastructure/docker-compose-neoload.yml"
    KOBITONJARPATH="$WORKSPACE/target/sampleproject-0.0.1-SNAPSHOT.jar"
    HOST="ec2-34-243-201-159.eu-west-1.compute.amazonaws.com"
    PORT="8780"
    APPLICATIONNAME="${HOST}:${PORT}"
    KOBITONCLOUDNAME="${env.cloudname}"
    NLAPI="${env.neoload_web_API_URL}"
    KOBITONJAR="sampleproject-0.0.1-SNAPSHOT.jar"
    ARGUMENT="-Dnl.selenium.proxy.mode=EndUserExperience"
    CONTROLLER="docker-ctl1"

    GROUP="NeotysKobiton"
    APP_NAME="PipelineExample"
  }
  stages {
     stage('Checkout') {
          steps {
              git  url:"https://github.com/${GROUP}/${APP_NAME}.git",
                      branch :'master',
                      credentialsId :'gituser'
          }
      }
    stage('Launch Appium Scripts on Kobiton') {
      steps {


            sh "mvn -B clean package  -DapplicationURL=$APPLICATIONNAME -DcloudName=${KOBITONCLOUDNAME}"

      }
    }



     stage('Start NeoLoad infrastructure') {

        steps {
                   sh "cp -f ${DOCKER_COMPOSE_TEMPLATE} ${DOCKER_COMPOSE_LG_FILE}"
                   sh "sed -i 's,TOKEN_TOBE_REPLACE,$NL_API_TOKEN,'  ${DOCKER_COMPOSE_LG_FILE}"
                   sh "sed -i 's,ZONE_TO_REPLACE,$ZONE,'  ${DOCKER_COMPOSE_LG_FILE}"
                   sh 'docker-compose -f ${DOCKER_COMPOSE_LG_FILE} up -d'
                   sleep 15

               }

     }


      stage('Attach Worker') {
         agent {
         docker {
             image 'python:3-alpine'
             reuseNode true
          }

        }
        stages {
                stage('Get NeoLoad CLI') {
                  steps {
                    withEnv(["HOME=${env.WORKSPACE}"]) {

                     sh '''
                          export PATH=~/.local/bin:$PATH
                          pip3 install neoload
                          neoload --version
                      '''

                    }
                  }
                }
                stage('Prepare Neoload test') {
                          steps {
                            withEnv(["HOME=${env.WORKSPACE}"]) {
                              sh "mkdir $WORKSPACE/neoload/konakart/custom-resources/"
                              sh "cp $WORKSPACE/target/${KOBITONJAR} $WORKSPACE/neoload/konakart/custom-resources/"
                              sh "sed -i 's/HOST_TO_REPLACE/${HOST}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"
                              sh "sed -i 's/PORT_TO_REPLACE/${PORT}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"
                              sh "sed -i 's/JAR_TO_REPLACE/${KOBITONJAR}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"
                              sh "sed -i 's/ARGUMENT_TO_REPLACE/${ARGUMENT}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"
                              sh "sed -i 's/TOKEN_TO_REPLACE/${APPLICATIONNAME}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"
                              sh "sed -i 's/IP_TO_REPLACE/${CONTROLLER}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"
                              sh "sed -i 's/CLOUD_TO_REPLACE/${KOBITONCLOUDNAME}/'  $WORKSPACE/neoload/konakart/variable_neoload.yaml"

                              sh """
                                     neoload \
                                     login --url ${NLAPI} ${NL_API_TOKEN} \
                                     test-settings create KobitonWebinar \
                                     project --path $WORKSPACE/neoload/konakart/ upload
                                """
                            }
                          }
                        }
                        stage('Run Test') {
                          steps {
                            withEnv(["HOME=${env.WORKSPACE}"]) {
                              sh """
                                   export PATH=~/.local/bin:$PATH
                                   neoload run \
                                  --return-0 \
                                  --as-code variable_neoload.yaml \
                                   KobitonWebinar
                                 """
                            }
                          }
                        }
                         stage('Generate Test Report') {
                                  steps {
                                    withEnv(["HOME=${env.WORKSPACE}"]) {
                                        sh """
                                             export PATH=~/.local/bin:$PATH
                                             neoload test-results junitsla
                                           """
                                    }
                                  }
                                  post {
                                      always {
                                          junit 'junit-sla.xml'
                                      }
                                  }
                        }

        }
      }


  }
  post {
        always {
                sh 'docker-compose -f ${DOCKER_COMPOSE_LG_FILE} down'
                sh 'docker volume prune'
                cleanWs()
        }

      }
}
