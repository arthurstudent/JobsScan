# Jobs Scan 

The Vacancy Scanner Project is a Java application designed to scrape and extract job vacancies from the [Techstars job portal](https://jobs.techstars.com/). 
The project provides functionalities to save, delete, and sort the extracted vacancies, making it easier for users to manage and review job opportunities.


## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Dockerization](#dockerization)

## Features

1. **Scraping Vacancies**: The application scrapes the Techstars job portal to extract the latest job vacancies with their relevant details like job title, company name, location, and job description.

2. **Save Vacancies**: Users can save interesting vacancies to a local database for future reference.

3. **Delete Vacancies**: Unwanted vacancies can be easily deleted from the local database to maintain an up-to-date and relevant list.

4. **Sort Vacancies**: The application offers sorting options based on various criteria such as job title, company name, or location.




## Getting Started

### Prerequisites

To run this project, you need to have the following installed:

- Java Development Kit (JDK) 17 or higher
- Docker (if you want to use Docker for containerization)
- PosgreSQL, if you want to run app locally 


## Installation
Clone the repository to your local machine:

```bash
git clone https://github.com/arthurstudent/JobsScan.git
```



## Usage

1. To build the Techstars job portal, run the application from IDE or use 
```bash
mvn clean install 
```

2. The application will retrieve the latest vacancies and display them in a Json format.

```bash
{
            "publicId": "4711434815",
            "jobTitle": "Software Developer",
            "companyName": "Hydrosat",
            "companyUrl": "https://apply.workable.com/hydrosat/j/3E669F0769?utm_source=Techstars+job+board&utm_medium=getro.com&gh_src=Techstars+job+board",
            "location": {
                "publicId": "8262807901",
                "country": "LUX",
                "city": "Luxembourg",
                "remote": "No Data"
            },
            "laborFunctions": [
                {
                    "publicId": "8022100710",
                    "laborFunction": "Software Engineering"
                }
            ],
            "logoLink": "https://cdn.filepicker.io/api/file/ykwoMdETfaAcBiY97wMN",
            "postedDate": 1690417224,
            "description": "<html>\n <head></head>\n <body>\n  <div data-testid=\"careerPage\">\n   <p>Hydrosat is a data analytics and satellite Earth observation company leveraging thermal infrared and multi-spectral data to deliver insights into crop health, drought and wildfire risk, industrial activity, and defense situational awareness to government and commercial customers. Our data analytics team in Luxembourg applies proprietary algorithms to thermal imagery and combines this with data fusion capabilities to extract valuable insights.</p>\n   <p>We are seeking an experienced software developer to support our team in Luxembourg in the development of our data analytics platform and commercial products. This role will appeal to candidates that are looking to use their software engineering skills to make an impact on the world in agriculture, climate, and public safety monitoring applications.</p>\n   <p><strong>What you’ll do:</strong></p>\n   <ul>\n    <li>Develop highly scalable, distributed services such as REST APIs and WebSockets.</li>\n   </ul>\n   <ul>\n    <li>Create multi-environment CI/CD pipelines for testing and deploying code.</li>\n    <li>Integrate data sources and work with the data science team to deliver data products to customer endpoints.</li>\n    <li>Design database schema and robust inter-server communication.</li>\n    <li>Build and optimise data processing tasks on our Kubernetes cluster.</li>\n    <li>Identify and mitigate technical and schedule risks during development.</li>\n   </ul>\n   <ul>\n    <li>Work with new and emerging technologies including Python data science tools, JavaScript/TypeScript and Docker/Kubernetes/Argo.</li>\n    <li>Design and implement customer facing and internal single page applications and dashboards.</li>\n   </ul>\n   <ul>\n    <li>5+ years of software development experience with multiple programming languages, technologies and frameworks</li>\n   </ul>\n   <ul>\n    <li>Proficiency in Python and/or JavaScript, with the ability to learn new languages and technologies quickly</li>\n    <li>Experience working with relational and NoSQL databases</li>\n    <li>Experience with Docker and microservices architecture</li>\n    <li>Experience with software development life cycle: design, development, test, deployment, monitoring and operations</li>\n    <li>Experience building and operating CI/CD pipelines</li>\n   </ul>\n   <ul>\n    <li>Highly self-motivated, keen learner able to solve challenging problems with creative solutions</li>\n    <li>Strong team player with demonstrated ability to take ownership and drive execution</li>\n    <li>Demonstrated ability to effectively collaborate with internal and external teams to deliver capability to users</li>\n   </ul>\n   <p><strong>Additional Qualifications Desired:</strong></p>\n   <ul>\n    <li>Experience taking software from conceptual stage to commercial product</li>\n    <li>Expertise and working knowledge with AWS or other cloud provider (GCP, Azure)</li>\n    <li>Experience helping Data Scientists build and operationalize code</li>\n    <li>Front-end development skills (React or VueJS).</li>\n   </ul>\n   <ul>\n    <li>Highly competitive salary</li>\n    <li>Employee equity</li>\n    <li>Flexible and dynamic startup environment</li>\n   </ul>\n  </div>\n </body>\n</html>"
        }
```

3. You can save vacancy like you can see above by POST method



4. In a root catalog of yhe app you can find Postman collections, that you can import and use



5. You can filer vacancies by params



## Dockerization

The Vacancy Scanner Project can be containerized using Docker for easy deployment and isolation. To build and run the Docker container, follow these steps:

1. Build the Docker image:

```bash
docker compose up.
```

2. You can run a postgres container:

```bash
docker run -e POSTGRES_PASSWORD=`<password>` `<imageId>`
```




