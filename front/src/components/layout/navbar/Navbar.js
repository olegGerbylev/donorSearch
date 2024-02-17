import classes from "./navbar.module.scss";
import {Link, useNavigate} from "react-router-dom";
import {ReactComponent as Logo} from '../../../media/logo.svg';
import {useContext, useEffect} from "react";
import RouterButton from "../../routerButton/RouterButton";
import PlaceIcon from '@mui/icons-material/Place';
import DropDown from "../../dropDown/DropDown";
import LocalSeeOutlinedIcon from '@mui/icons-material/LocalSeeOutlined';
import {Context} from "../../../index";
import {observer} from "mobx-react-lite";


const options = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
];


const Navbar = observer(({type}) => {

    const {user} = useContext(Context)
    const navigate = useNavigate()
    const handleSelect = (option) => {
        console.log('Selected option:', option);
    };


    return (
        <div className={classes.navbar}>
            <div className={classes.container}>
                <div className={classes.left}>
                    <Link to="/" className={classes.logo}>
                        YPI
                        <div className={classes.logoTeg}>
                            #YourPetsSuperHero
                        </div>
                    </Link>
                </div>
                {type === "max" &&
                    <div className={classes.right}>
                        <img src={require('../../../media/superCat.png')} className={classes.catImg}/>
                        <Link to={"/map"} className={classes.linkToMap}>
                            где сдать кровь?
                            <PlaceIcon/>
                        </Link>
                        {user.isAuth
                            ?
                            <div onClick={()=>{navigate('/home')}} className={classes.photo}>
                                {user.photo ?
                                    <img src={require('../../../media/img2.png')} className={classes.profileImg}/>
                                    :
                                    <LocalSeeOutlinedIcon/>
                                }
                            </div>
                            :
                            <div className={classes.loginButton} onClick={(e)=>navigate('/login')}>войти</div>
                        }
                    </div>
                }
            </div>
        </div>
    );
});

export default Navbar;