import {IPublication, IReaction} from "../../assets/models/Publication";
import React, {useEffect, useState} from "react";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import CommentForm from "./CommentForm";
import ReactionCard from "./ReactionCard";

interface ReactionProps {
    publication: IPublication;
    username: string;
}

function Reactions({publication, username}: ReactionProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const [reactions, setReactions] = useState<IReaction[]>([]);

    useEffect(() => {
        cookbookService.getReactionsByPublication(publication)
            .then((response) => {
                setReactions(response);
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
            });
    }, [publication]);

    return (
        <div id={"reactions"} className={""}>
            <h1 className={`text-3xl`}>{t('reactions')}</h1>

            {
                reactions.filter((reaction) => reaction.cookUsername === username).length === 0 &&
                <CommentForm setReactions={setReactions} reactions={reactions} publication={publication}
                             username={username}/>
            }

            <div className={"w-1/3 mx-auto"}>
                {
                    reactions.map((reaction, index) => (
                        <div key={index}>
                            <ReactionCard reaction={reaction}/>
                        </div>
                    ))
                }
            </div>
        </div>
    );
}

export default Reactions;