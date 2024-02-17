import React, {createContext, useEffect} from 'react';
import ReactDOM from 'react-dom/client';
import App from "./App";
import UserStore from "./context/UserStore";
import ErrorStore from "./context/ErrorStore";

export const Context = createContext(null)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Context.Provider value={{user: new UserStore(), error: new ErrorStore()}}>
            <App/>
        </Context.Provider>
    </React.StrictMode>
);
