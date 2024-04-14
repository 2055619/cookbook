import {IReaction} from "../../assets/models/Publication";
import React from "react";
import {useNavigate} from "react-router-dom";
import {faStar} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

interface IReactionCardProps {
    reaction: IReaction;
}

function ReactionCard({reaction}: IReactionCardProps) {
    const navigate = useNavigate();

    function handleViewProfile(event: any) {
        event.stopPropagation();
        navigate('/u/profile?username=' + reaction.cookUsername)
    }

    function getDateString() {
        const date = new Date(reaction.comment!.creationDate);
        const timeDiff = Date.now() - date.getTime();
        if (timeDiff < 60000) {
            return "Just now";
        }
        if (timeDiff < 3600000) {
            return Math.floor(timeDiff / 60000) + " minutes ago";
        }
        if (timeDiff < 86400000) {
            return Math.floor(timeDiff / 3600000) + " hours ago";
        }
        return Math.floor(timeDiff / 86400000) + " days ago";
        // return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    }

    return (
        <div
            className="flex flex-col justify-between w-full h-full p-4 max-w-md mx-auto rounded-xl shadow-md overflow-hidden md:max-w-2xl m-3">
            <div className="flex justify-between w-full">
                <div className="flex space-x-2">
                    <span className={"tracking-wide font-semibold clickable hover:bg-cook-red hover:rounded-full p-1"}
                          onClick={handleViewProfile}>
                        {reaction.cookUsername}
                    </span>
                    <span className={"font-light mt-1"}>{getDateString()}</span>
                </div>
                <div>
                    {[...Array(5)].map((star, index) => {
                        return (
                            <FontAwesomeIcon
                                key={index}
                                icon={faStar}
                                className={` ${index < Math.floor(reaction.rating!) ? 'text-cook-light' : 'text-cook'} `}
                            />
                        );
                    })}

                </div>
            </div>
            <div className="mt-1 ms-5 text-left">{reaction.comment?.content}</div>
        </div>
    );
}

export default ReactionCard;