import './App.css';

import MapWithUserLocation from "./MapWithUserLocation";
import Form from "./Form";


function App() {

  return (
    <div style={{width: "100%", height: "100vh"}}>
        <MapWithUserLocation/>
        <Form/>
    </div>
  );
}

export default App;
