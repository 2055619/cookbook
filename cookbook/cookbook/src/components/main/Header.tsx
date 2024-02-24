import {useTranslation} from "react-i18next";
import LanguageSelector from "../Utils/LanguageSelector";
import {NavLink, useNavigate} from "react-router-dom";
import logo from "../../logo.svg";
import {IUser} from "../../assets/models/Authentication";
import {Button} from "react-bootstrap";

interface IHeaderProps {
    setUser: (user: IUser | null) => void;
    user: IUser | null;
}

function Header({user, setUser}: IHeaderProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();

    function SignOut() {
        setUser(null);
        localStorage.clear();
        navigate('/');
    }

    return (
        <header className={"bg-light-cook mx-auto row"}>
            <NavLink className="m-3 text-decoration-none text-white col-3 p-0" to="/">
                <div className="d-flex icon-btn p-0">
                    <img alt="Logo" className="col-3 d-none d-md-block" src={logo}/>
                    <h1 className={"display-6 fw-semibold my-auto"}>{t('name')}</h1>
                </div>
            </NavLink>
            <div className={"text-center mx-auto my-auto col-4"}>
                <p>{t('summedDescription')}</p>
            </div>
            <LanguageSelector/>
            {user === null ?
                <div className={"col-2  my-auto text-center"}>
                    <Button className="btn btn-outline-cook "
                            variant={"btn-outline-cook"}
                            onClick={() => {
                                navigate('/authentication/signin')
                            }}>
                        {t('signin')}
                    </Button>
                </div> :
                <div className={"col-2 my-auto text-center"}>
                    <p className={"h5 mx-0 p-0"}>{t('welcome') + " " + user.username}</p>
                    <Button className="btn btn-outline-cook"
                            variant={"btn-outline-cook"}
                            onClick={SignOut}>
                        {t('signout')}
                    </Button>
                </div>
            }
        </header>
    );
}

export default Header;