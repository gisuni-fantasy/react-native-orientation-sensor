
# react-native-orientation-sensor

## Introduction

This is a react-native native library, the library is only available for android. We can get the direction data.

## Getting started

`$ npm install react-native-orientation-sensor --save`

### Mostly automatic installation

`$ react-native link react-native-orientation-sensor`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-orientation-sensor` and add `RNOrientation.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNOrientation.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.agree.orientationsensor.RNOrientationPackage;` to the imports at the top of the file
  - Add `new RNOrientationPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-orientation-sensor'
  	project(':react-native-orientation-sensor').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-orientation-sensor/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-orientation-sensor')
  	```


## Usage
```javascript
import orientationObservable from 'react-native-orientation-sensor';

// TODO: orientationObservable is Rxjs.Observable
// degree is direction angle
const subscribtion = orientationObservable.subscribe(
  // angle range [-180, 180]
  degree => console.log(degree)
)

setTimeout(() => {
  subscribtion.unsubscribe()
}, 1000)
```
  