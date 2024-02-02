import './App.css';

import MapWithUserLocation from "./MapWithUserLocation";
import Form from "./Form";
import {useState} from "react";


function App() {
    const [position, setPosition] = useState([])
    const [mapState, setMapState] = useState("viewing")

    const changeState = () =>{

    }

    return (
    <div style={{width: "100%", height: "100vh"}}>
        <button>add position</button>
        <MapWithUserLocation/>
        <Form/>
    </div>
  );
}

export default App;
