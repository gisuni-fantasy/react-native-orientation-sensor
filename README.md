
# react-native-orientation

## Getting started

`$ npm install react-native-orientation --save`

### Mostly automatic installation

`$ react-native link react-native-orientation`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-orientation` and add `RNOrientation.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNOrientation.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.agree.orientationsensor.RNOrientationPackage;` to the imports at the top of the file
  - Add `new RNOrientationPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-orientation'
  	project(':react-native-orientation').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-orientation/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-orientation')
  	```


## Usage
```javascript
import RNOrientation from 'react-native-orientation';

// TODO: What to do with the module?
RNOrientation;
```
  