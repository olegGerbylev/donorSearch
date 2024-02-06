import {
    createBrowserRouter,
    RouterProvider,
    Route,
    Outlet,
    Navigate,
} from "react-router-dom";
import './style.scss';
import { useContext } from "react";
import { DarkModeContext } from "./context/darkModeContext";
import { AuthContext } from "./context/authContext";
import Login from "./pages/login/Login";
import Map from "./pages/map/Index"
import Layout from './components/layout/Index'

function App() {
    const {currentUser} = useContext(AuthContext);
    const { darkMode } = useContext(DarkModeContext);


    const ProtectedRoute = ({ children }) => {
        if (false) {
            return <Navigate to="/login" />;
        }
        return children;
    };

    const router = createBrowserRouter([
        {
            path: "/",
            element: (
                <Layout />
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
                    path: "/login",
                    element: <Login/>,
                },
                {
                    path: "/register",
                    element: <div>reg</div>,
                },
                {
                    path: "/*",
                    element: <div>404</div>
                }
                // {
                //     path: "/profile/:id",
                //     element: <Profile />,
                // },
            ],
        },
    ]);

    return (
        <div className={`theme-${darkMode ? "dark" : "light"}`}>
            <RouterProvider router={router} />
        </div>
    );
}

export default App;
