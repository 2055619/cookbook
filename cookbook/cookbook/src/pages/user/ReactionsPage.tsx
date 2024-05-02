import Reactions from "../../components/reaction/Reactions";
import React, {useEffect, useState} from "react";
import {IPublication} from "../../assets/models/Publication";
import {toast} from "react-toastify";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {IUser} from "../../assets/models/Authentication";
import ImageCard from "../../components/publications/ImageCard";
import PublicationCard from "../../components/publications/PublicationCard";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";

interface IReactionsPageProps {
    username: string;
}

function ReactionsPage({username}: IReactionsPageProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();

    const [publication, setPublication] = useState<IPublication | null>(null);

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');

        if (title) {
            cookbookService.getPublication(title)
                .then((response) => {
                    setPublication(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        } else {
            toast.error(t('noPublicationForTitle'));
        }

    }, []);

    return (
        <div>
            <div className={"text-start ms-1 sticky top-14"}>
                <button onClick={() => window.history.back()}
                        className="clickable hover:bg-cook-red hover:rounded-full px-2 py-1">
                    <FontAwesomeIcon icon={faArrowLeft}/>
                </button>
            </div>

            <h1 className={"text-5xl"}>{publication?.title}</h1>
            <h1 className={"text-3xl"}>{publication?.description}</h1>
            <div className={"my-5"}></div>
            <Reactions publication={publication!} username={username}/>
        </div>
    );
// if (loading) {
//   return <Loading />;
// }

}

export default ReactionsPage;