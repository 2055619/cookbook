import {IPublication, IReaction} from "../../assets/models/Publication";
import React, {useEffect, useState} from "react";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import CommentForm from "./CommentForm";
import ReactionCard from "./ReactionCard";
import {faStar} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import StarDropdown from "./StarDropdown";

interface ReactionProps {
    publication: IPublication;
    username: string;
}

function Reactions({publication, username}: ReactionProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();

    const [reactions, setReactions] = useState<IReaction[]>([]);
    const [avgRating, setAvgRating] = useState(0);
    const [newRating, setNewRating] = useState(0);
    const [starFilter, setStarFilter] = useState(0);
    const stars = [1, 2, 3, 4, 5];

    useEffect(() => {
        cookbookService.getReactionsByPublication(publication)
            .then((response) => {
                setReactions(response);
                let sum = 0;
                response.map((reaction) => {
                    sum += reaction.rating!;
                    return reaction;
                });
                setAvgRating(sum / response.length);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken" && error.message !== "No Publication ID"){
                    toast.error(t(error.response?.data.message));
                }
            });

    }, [publication, newRating]);

    return (
        <div id={"reactions"} className={"mt-2"}>
            <div className={"text-3xl"}>
                <h1 className={``}>{t('reactions')}</h1>

                {stars.map((star, index) => {
                    return (
                        <FontAwesomeIcon
                            key={index}
                            icon={faStar}
                            className={` ${index < Math.floor(avgRating) ? 'text-cook-light' : 'text-cook'} `}
                        />
                    );
                })}
            </div>
            {
                reactions.filter((reaction) => reaction.cookUsername === username).length === 0 &&
                <CommentForm setReactions={setReactions} reactions={reactions} publication={publication}
                             username={username} setNewRating={setNewRating}/>
            }

            <label className={"text-2xl me-3"}>{t('starFilter')}</label>
            <StarDropdown selectedStars={starFilter} setSelectedStars={setStarFilter}/>

            <div className={"w-2/3 mx-auto"}>
                {
                    reactions
                        .filter((reaction) => starFilter === 0 || reaction.rating! >= starFilter)
                        .map((reaction, index) => (
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