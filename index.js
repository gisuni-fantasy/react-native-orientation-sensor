import { NativeModules, DeviceEventEmitter } from "react-native";
import { Observable } from "rxjs";
import { refCount, publish} from 'rxjs/operators'
const { RNOrientation } = NativeModules;

function createSensorObservable() {
  return Observable.create(function subscribe(observer) {
    this.isSensorAvailable = false;

    this.unsubscribeCallback = () => {
      if (!this.isSensorAvailable) return;
      RNOrientation.stopUpdates();
    };

    RNOrientation.isAvailable().then(
      () => {
        DeviceEventEmitter.addListener("RNOrientation", data => {
          observer.next(data);
        });

        this.isSensorAvailable = true;

        RNOrientation.startUpdates();
      },
      () => {
        observer.error(`Orientation sensor is not available`);
      }
    );

    return this.unsubscribeCallback;
  }).pipe(publish(), refCount());
}

export default createSensorObservable()

// export default RNOrientation;
