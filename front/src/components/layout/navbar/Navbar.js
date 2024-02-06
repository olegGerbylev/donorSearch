import "./navbar.scss";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import WbSunnyOutlinedIcon from "@mui/icons-material/WbSunnyOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import { Link } from "react-router-dom";
import {ReactComponent as Logo} from '../../../media/logo.svg';
import { useContext } from "react";
import { DarkModeContext } from "../../../context/darkModeContext";
import { AuthContext } from "../../../context/authContext";
import RouterButton from "../../routerButton/RouterButton";
import PlaceIcon from '@mui/icons-material/Place';
import DropDown from "../../dropDown/DropDown";


const options = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
];


const Navbar = () => {
    // const { toggle, darkMode } = useContext(DarkModeContext);
    // const { currentUser } = useContext(AuthContext);

    const {currentUser} = useContext(AuthContext);

    const handleSelect = (option) => {
        console.log('Selected option:', option);
        // Ваша логика обработки выбора опции
    };

    const isLogin = () => {
        return false
    }

    return (
        <div className={"navbar"}>
            <div className={"container"}>
                <div className={"left"}>
                    <Link to="/fdsf">
                        <Logo/>
                    </Link>
                    <RouterButton text={"Кто может стать донором?"} path={"/info"} id={"route"}/>
                    <RouterButton text={"Бонусы для доноров"} path={"/about"} id={"route"}/>
                    {/*<div>*/}
                    {/*    <DropDown options={options} onSelect={handleSelect} />*/}
                    {/*</div>*/}
                </div>
                <div className={"right"}>
                    <Link to={"/map"} className={"linkToMap"}>
                        где сдать кровь?
                        <PlaceIcon/>
                    </Link>
                    {isLogin()
                        ?
                        <div>Тут типо фото...</div>
                        :
                        <div className={"loginButton"}>войти</div>
                    }
                </div>
            </div>
        </div>
    );
};

export default Navbar;