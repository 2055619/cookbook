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
        <header className={"bg-light-cook mx-auto my-auto p-1 row "}>
            <NavLink className="m-3 col-2 text-decoration-none text-white col-2" to="/">
                <div className="d-flex icon-btn">
                    <img alt="Logo" className="col-4 d-none d-md-block" src={logo}/>
                    <h1 className={"display-6 fw-semibold"}>{t('name')}</h1>
                </div>
            </NavLink>
            <div className={"text-center mx-auto my-auto col-5"}>
                <p>{t('summedDescription')}</p>
            </div>
            <div className={"col-1"}>
                <LanguageSelector/>
            </div>
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
                    <Button className="btn btn-outline-cook "
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