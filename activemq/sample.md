# ActiveMQ Connector

Apache ActiveMQ is a popular open source messaging provider which is easy to integrate with Mule. ActiveMQ supports the JMS 1.1 and J2EE 1.4 specifications and is released under the Apache 2.0 License.

### Contents

[Prerequisites](#prerequisites)    
[Step 1: Create Demo Project](#step-1-create-demo-project)  
[Step 2: Configure ActiveMQ connector](#step-2-configure-activemq-connector)  
[Step 3: Create newQueue flow](#step-3-create-newqueue-flow)  
[Step 4: Run project](#step-4-run-project)   
[Step 5: Test newQueue flow](#step-5-test-newqueue-flow)  
[Step 6: Create newTopic flow](#step-6-create-newtopic-flow)  
[Step 7: Test newTopic flow](#step-7-test-newtopic-flow)  
[Other Resources](#other-resources)  

### Prerequisites

* [MuleStudio](http://www.mulesoft.org/download-mule-esb-community-edition)
* Installed and running [ActiveMQ](http://activemq.apache.org/). In this example, we use ActiveMQ 5.8.0 release.

### Step 1: Create Demo Project

* Run Mule Studio and select **File \> New \> Mule Project** menu item.  
* Type **activemq_demo** as a project name and click **Next**.  

![Create Demo project](images/step1-1.png)

* Then click **Finish**.

![Create Demo project](images/step1-2.png)

### Step 2: Configure ActiveMQ connector

* Open **flows/activemq_demo.mflow** file. Select **Global Elements** tab, click **Create** button and using the filter find and select the **Connectors \> JMS \> ActiveMQ** connector. Click **OK**. You will see a window for global configuration of the ActiveMQ connector, adjust the fields as displayed on the following image.

![Configure ActiveMQ connector](images/step2-1.png)

* To configure the project in order to use ActiveMQ libraries, right-click your project, select **Build Path** and choose **Add External Archives**. Choose the **activemq-all-5.8.0.jar** file from the **/lib** directory of the demo project.

![Configure ActiveMQ connector](images/step2-2.png)

### Step 3: Create newQueue flow

* Switch to the **Message Flow** tab in the flow editor.
* Add a new flow by dragging it from the Palette.
* Double click the new flow to open its properties and rename it to **newQueue**. Click **OK**.

![Create newQueue flow](images/step3-1.png)

* Drag **HTTP Endpoint** to the flow. Double click it to show its properties and adjust them as displayed on the following image.

![Create newQueue flow](images/step3-2.png)

* Drag **JMS Endpoint** to the flow. Double click it to show its properties. Set **Generic \> Queue** as **newQueue**.

![Create newQueue flow](images/step3-3.png)

* Switch to the **References** tab and in the **Connector Reference** dropdown select  **Active_MQ** which we configured earlier on Step 2. Click **OK**.

![Create newQueue flow](images/step3-4.png)

* Save the flow.

### Step 4: Run project

* Right Click **src/main/app/activemq_demo.xml \> Run As/Mule Application**.

![Run Project](images/step4-1.png) 

* Check the console to see when the application starts.  

You should see a log message on the console:  
 
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    
    + Started app 'activemq_demo'                              +    
    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  

### Step 5: Test newQueue flow

* Run ActiveMQ using executable **activemq** from the **$ACTIVEMQ_HOME/bin** folder. Make sure you can access the ActiveMQ administration page using [http://localhost:8161/](http://localhost:8161/).

![Test newQueue flow](images/step5-1.png)

* Run the project.
* Open your browser and point it to [http://localhost:8081/jms](http://localhost:8081/jms).
* You should see a **/jms** message in the browser's window.

![Test newQueue flow](images/step5-2.png)

* Check sent messages by examining the ActiveMQ administration page at [http://localhost:8161/admin/queues.jsp](http://localhost:8161/admin/queues.jsp). You can see that there is **1** message under the **Messages Enqueued** column in the **newQueue** row.

![Test newQueue flow](images/step5-3.png)

* Stop Mule server.

### Step 6: Create newTopic flow

* Add a new flow by dragging it from the Palette.
* Double click the new flow to open its properties and rename it to **newTopic**. Click **OK**.

![Create newTopic flow](images/step6-1.png)

* Drag **JMS Endpoint** to the flow. Double click it to show its properties. Set **Generic \> Topic** as **newTopic**.

![Create newTopic flow](images/step6-2.png)

* Switch to the **References** tab and in the **Connector Reference** dropdown select **Active_MQ** which we configured earlier on Step 2.
* Drag **JMSMessage to Object** and **Object to String** transformers to the flow. They do not need any additional settings.

![Create newTopic flow](images/step6-3.png)

* Drag **Logger** component to the flow. Double click it to show its properties and adjust them as displayed on the following image.

![Create newTopic flow](images/step6-4.png)

* Save the flow.

### Step 7: Test newTopic flow

* Run ActiveMQ using executable **activemq** from the **$ACTIVEMQ_HOME/bin** folder. Make sure you can access the ActiveMQ administration page using [http://localhost:8161/](http://localhost:8161/).
* Run the project.
* Open your browser and point it to [http://localhost:8161/admin/topics.jsp](http://localhost:8161/admin/topics.jsp). Set the **Topic Name** text field as **newTopic** and push **Create** button. 

![Test newTopic flow](images/step7-1.png)
 
* You will see the **newTopic** row in the **Topics** list table on this page.
* Click the **Send To** link in the **newTopic** row. You will see a form for a new message. In the **Message body** field write **Hello from ActiveMQ.** message and push **Send** button. 

![Test newTopic flow](images/step7-2.png)

* You can see that there is **1** message under the **Messages Enqueued** column in the **Topics** list table [http://localhost:8161/admin/topics.jsp](http://localhost:8161/admin/topics.jsp).
* In MuleStudio console you can see the logger message

```
[[activemq_demo].newTopic.stage1.02] org.mule.api.processor.LoggerMessageProcessor: Hello from ActiveMQ.
```

* Stop Mule server.

### Other Resources

For more information on:

- Mule AnyPoint® connectors, please visit [http://www.mulesoft.org/connectors](http://www.mulesoft.org/connectors)
- Mule platform and how to build Mule applications, please visit [http://www.mulesoft.org/documentation/display/current/Home](http://www.mulesoft.org/documentation/display/current/Home)