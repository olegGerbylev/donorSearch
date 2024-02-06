import React, {useEffect} from 'react';
import ReactDOM from 'react-dom/client';
import {AuthContextProvider} from "./context/authContext";
import {DarkModeContextProvider} from "./context/darkModeContext";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <DarkModeContextProvider>
            <AuthContextProvider>
                <App/>
            </AuthContextProvider>
        </DarkModeContextProvider>
    </React.StrictMode>
);
