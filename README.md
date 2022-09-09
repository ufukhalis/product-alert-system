# Product Alert System

This project is an example of microservices architecture to demonstrate it. 
The goal of this system, to notify users when the product that they subscribed has lower price than the expected one.

The system contains three services/components.

### product-parser-job

It's basically simulates the product stream that user can be notified. For the simplicity, it just generates same products with different prices for each run(It's a cron-job).
It produces the Product data to Kafka and saves Product Information to MongoDB to be used later.

> Note: This job will run for each minute in our k8s.

### user-subscriber-service

This is the entry-point to our system. It exposes the API and the API can be reached via Swagger url. Basically, a user can be subscribed to a product or delete the subscription.
Additionally, it exposes the query API for the product-alert-service to get users whom to notify.


### product-alert-service

This one is the decision maker that understands whom to notify based on the lower price. It reads the data Kafka and make the decision to notified users(Currently, it does not send the email in real, so it just writes that in the logs).

> Note: In the project, you will be able to see docker-compose.yml file which contains MongoDB, Kafka, Zookeeper and Kafka-UI applications.
> If you would like to run the project in a normal way, you can first run that docker-compose and then run each project one by one. Do not forget to change some properties in the application.properties file.
> 
> But the aim of this project, to have all services are running in the k8s env. So, this project uses microK8s, but you can use also whatever you want.


## Prerequisites
* Install [microK8s](https://microk8s.io) and enabled the addons such as DNS, Dashboard etc.
* Install docker
* Install JDK, Maven (If you would like to compile or run project normally)

> Note: All needed services/components have been already pushed to public docker registry under ufukhalis namespace so once your local k8s will try to pull from there.

Once you have installed the microk8s, you can start it via below command.

```shell
microk8s start
```

And then you can run the below one to have dashboard.

```shell
microk8s dashboard-proxy
```

This command will give an output for available IP address of the dashboard and also the access token to login your local k8s dashboard.

Once you are able to login there and there is no issue then you can continue with creating `MongoDB` in our k8s.
We have `mongo-deployment.yml` file so run the below command.

```shell
microk8s kubectl create -f mongo-deployment.yml
```

And same for `Kafka`.

```shell
microk8s kubectl create -f kafka-deployment.yml
```

After that, you can check if both deployments are okay in the dashboard. 

Each project folder contains the `deployment.yml` file so try to enter each folder and run the below command again.

```shell
cd user-subscriber-service
microk8s kubectl create -f deployment.yml
```

Once this is done then again check the pods and also the logs that the application is working or not.

Currently, we need a user to subscribe a product so we need to access the Swagger interface from the deployed `user-subscriber-service`.
From the dashboard, go to the `Replica Sets` and enter the `user-subscriber-service-app`. And there is `Internal Endpoints` section that contains two values. So, we need the port number from the second one.
After that, you can use the IP address which is the same for your dashboard and use that port like below.

```shell
http://{IP_ADDRESS_DASHBOARD}:
{PORT_NUMBER}/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/user-subscriber-controller/saveUser
```

>Note: The `PUT` method waiting a productId from us. So, currently, our parser-job generates the same product ids for each run with different prices. So, you can pick your product id between 1 and 100. Like `product5`.

For example, we have a request body like below.

```json
{
  "email": "ufukhalis@gmail.com",
  "productId": "product5",
  "lowerThanPrice": 80
}
```

So, this user has subscribed with that email and if for that specific product, the price is lower than that specified price(80) than our `product-alert-service` will notify that user(You will see that in the service logs).

If you have done with testing or playing with the project than you can stop the microk8s like below command.

```shell
microk8s stop
```

### TODOs
* Add gateway application
* Add monitoring(Grafana?)
* Add collecting and visualizing the logs(ElasticSearch and Kibana?)
* Add diagram of the system
