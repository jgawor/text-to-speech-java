# Text to Speech Java Starter Application

  The IBM Watson [Text to Speech][service_url] service is designed for streaming, low latency, synthesis of audio from text. It is the inverse of the automatic speech recognition. 

## Getting Started

1. Build the project. You need to use [Apache Maven](https://maven.apache.org/) to build the Java application.
  ```sh
  $ mvn install
  ```

2. Start application on WebSphere Liberty. Maven will automatically download and install WebSphere Liberty.
  ```sh
  $ mvn liberty:start-server
  ```

  The applicatin should be running on [http://localhost:9080/text2speech/](http://localhost:9080/text2speech/). However, if you press on the `Download` or `Speak` button things will not work until you bind the `Text to Speech` service configuration in step 6.

3. Login to Bluemix. [Sign up][sign_up] in Bluemix, or use an existing account. 
  ```sh
  $ ./target/wlp/bin/bluemixUtility login --api=us-south
  ```

4. Create the `Text to Speech` service instance in Bluemix.
  ```sh
  $ ./target/wlp/bin/bluemixUtility createService text_to_speech standard text-to-speech-service
  ```

5. Import configuration for the service.
  ```sh
  $ ./target/wlp/bin/bluemixUtility import text-to-speech-service
  ```

6. Bind the imported configuration to a Liberty server.
  ```sh
  $ ./target/wlp/bin/bluemixUtility bind defaultServer text-to-speech-service
  ```

  Access the application again on [http://localhost:9080/text2speech/](http://localhost:9080/text2speech/) and press on the `Speak` button.

## License

  This sample code is licensed under Apache 2.0. Full license text is available in [LICENSE](LICENSE).
  The sample uses jQuery which is licensed under MIT.

## Contributing

  See [CONTRIBUTING](CONTRIBUTING.md).

## Open Source @ IBM

  Find more open source projects on the
  [IBM Github Page](http://ibm.github.io/).

[service_url]: http://www.ibm.com/smarterplanet/us/en/ibmwatson/developercloud/text-to-speech.html
[cloud_foundry]: https://github.com/cloudfoundry/cli
[sign_up]: https://apps.admin.ibmcloud.com/manage/trial/bluemix.html?cm_mmc=WatsonDeveloperCloud-_-LandingSiteGetStarted-_-x-_-CreateAnAccountOnBluemixCLI
[liberty]: https://developer.ibm.com/wasdev/downloads/
[liberty_mac]: http://www.stormacq.com/how-to-install-websphere-8-5-liberty-profile-on-mac/
[ant]: http://ant.apache.org/bindownload.cgi
