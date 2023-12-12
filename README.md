# SatTrack
![Satellite Tracker](https://link.to/your/screenshot.png)

Satellite Tracker is an open-source Java program that leverages the ArcGIS API and N2YO API to provide real-time tracking of satellites. This tool allows users to visualize the current locations of satellites, providing valuable information for tracking and monitoring purposes.



## Table of Contents

- Introduction
- Getting Started
  - Requirements
  - Installation and Setup
  - Deployment



## Introduction

### Features

- Real-time satellite tracking using ArcGIS and N2YO APIs.
- Interactive and user-friendly graphical interface.
- Displays detailed information about each tracked satellite, including name, NORAD ID, altitude, azimuth, and elevation.
- Customizable settings for filtering and displaying specific satellites.

## Getting Started

### Requirements

Before running the Satellite Tracker, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK) 8 to 20
- ArcGIS API key (sign up [here](https://developers.arcgis.com/))
- N2YO API key (obtainable [here](https://www.n2yo.com/api/))
- IntelliJ IDEA (recommended for instructions ahead)



### Installation

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/snowclipsed/SatTrack
   ```

2. Navigate to the project directory:

   ```bash
   cd SatTrack
   ```

3. Open the directory as an IntelliJ IDEA Project.

<img src="C:\Users\harry\AppData\Roaming\Typora\typora-user-images\image-20231211212109800.png" alt="image-20231211212109800" style="zoom:50%;" />

4. In IntelliJ IDEA, open the `src/main/java/com.example/app/Constants.java` file and provide your ArcGIS and N2YO API keys:

```properties
public static String ARCAPI = "your-arcgis-api-key";
public static String N2YOAPI = "your-n2yo-api-key";
```

5. To build the project using Gradle:
   1. Open the  `build.gradle` file and run it.
   2. Open the Gradle sidebar (if you do not have the sidebar, go to View > Tool Windows > Gradle).
   3. On the sidebar, double click the copyNatives function to run it. This will install some ArcGIS .jar files.

### Deployment

To deploy the project, run `src/main/java/com.example/app/App.java`. 

This will open the application window. **Make sure to fullscreen the application window once so graphic elements snap to the right place.**



## Usage

Run the application using the following command:

```bash
java -jar target/satellite-tracker.jar
```

Once the application is running, open your web browser and navigate to [http://localhost:8080](http://localhost:8080) to access the Satellite Tracker interface.



## Configuration

You can customize the Satellite Tracker by modifying the `src/main/resources/application.properties` file. Adjust parameters such as update frequency and default map zoom level according to your preferences.

```properties
# Application Configuration
satellite.tracker.update.interval=5000 # Update interval in milliseconds
satellite.tracker.default.zoom.level=5   # Default map zoom level
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



## Acknowledgments

- Thanks to [ArcGIS](https://developers.arcgis.com/) for providing the powerful mapping capabilities.
- Thanks to [N2YO](https://www.n2yo.com/) for the comprehensive satellite tracking API.
- Thanks to Professor Brianna Dym for her constant support throughout this semester.
- Thanks to Dipti Kulkarni for educating me about ArcGIS API.
- Thanks to Rashaad M. Mirza for testing my program.



## Support

For any questions or issues, please [open an issue](https://github.com/your-username/satellite-tracker/issues) or contact me at bishnoi.h@northeastern.edu.

---

