import React, {useContext, useState} from 'react';
import MapWithUserLocation from "./map/MapWithUserLocation";
import Form from "./form/Form";
import {AuthContext} from "../../context/authContext";
import Info from "./info/Info";

const Index = () => {
    const { currentUser } = useContext(AuthContext);
    const [mapState, setMapState] = useState("viewing")

    return (
        <div>
            {/*{(mapState === "edit" && currentUser.id === -1) ? <Form/>: <Info/>}*/}
            {/*<Form/>*/}
            <Info/>
            <MapWithUserLocation/>
        </div>
    );
};

export default Index;