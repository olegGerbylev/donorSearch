import {
    createBrowserRouter,
    RouterProvider,
    Route,
    Outlet,
    Navigate,
} from "react-router-dom";
import './style.scss';
import Login from "./pages/login/Login";
import Map from "./pages/map/Index"
import Layout from './components/layout/Index'
import {useContext, useEffect, useState} from "react";
import {Context} from "./index";
import {observer} from "mobx-react-lite";
import Error from "./components/error/Error";
import Register from "./pages/register/Register";
import RegistrationCompleted from "./pages/registrationCompleted/RegistrationCompleted";
import RegistrationConfirmAccount from "./pages/registrationConfirmAccount/RegistrationConfirmAccount";
import axios from "axios";
import NotFound from "./pages/notFound/NotFound";
import Home from "./pages/home/Home";

const App = observer(()=> {

    const {user, error} = useContext(Context)
    const [isLoading, setIsLoading] = useState(false)

    // // Here, code praying to the GOD for protecting my code from bugs and other things.
    // // This is really crucial step! Be adviced to not remove it, even if you don't believer.
    // useEffect(()=>{
    //     console.log("")
    // },[])

    useEffect(()=>{
        if (localStorage.getItem("sessionId")) {
            axios.post('http://localhost:8080/api/checkSession', {},
                {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem("sessionId"),
                    }
                }).then(function (response) {
                user.setIsAuth(true)
                user.role = response.data.permission
                user.displayName = response.data.displayName
                setIsLoading(true)
            }).catch(function (e) {
                user.logOut()
                setIsLoading(true)
            })
        }else {
            setIsLoading(true)
        }
    },[])

    const ProtectedRoute = ({ children }) => {
        if (!user.isAuth) {
            return <Navigate to="/login" />;
        }
        return children;
    };

    const UnLoginRoute = ({ children }) => {
        if (user.isAuth) {
            return <Navigate to="/" />;
        }
        return children;
    };

    const router = createBrowserRouter([
        {
            path: "/",
            element: (
                <Layout type={"max"}/>
            ),
            children: [
                {
                    path: "/",
                    element: <div>secure path</div>,
                },
                {
                    path: "/map",
                    element: <Map/>,
                },
                {
                    path: "/home",
                    element: <ProtectedRoute><Home/></ProtectedRoute>,
                },
            ],
        },
        {
            path: "/",
            element:(
                <Layout type={"min"}/>
            ),
            children:[
                {
                    path: "/login",
                    element: <UnLoginRoute><Login/></UnLoginRoute>,
                },
                {
                    path: "/register",
                    element: <UnLoginRoute><Register/></UnLoginRoute>,
                },
                {
                    path: "/registration-success",
                    element: <RegistrationCompleted/>,
                },
                {
                    path: "/registration-completed/:id",
                    element: <RegistrationConfirmAccount/>,
                },
                {
                    path: "/*",
                    element: <NotFound/>
                }
            ]
        },
    ]);

    return (
        <>
            {isLoading ?
                <>
                    {error.message && <Error/> }
                    <RouterProvider router={router} />
                </>
            :
                <div>
                    Loading...
                </div>
            }
        </>
    );
})

export default App;
