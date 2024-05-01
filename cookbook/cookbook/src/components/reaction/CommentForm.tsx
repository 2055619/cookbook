import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPaperPlane, faStar} from "@fortawesome/free-solid-svg-icons";
import React, {useState} from "react";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {IPublication, IReaction} from "../../assets/models/Publication";
import {toast} from "react-toastify";

interface CommentsCardProps {
    setReactions: (reactions: IReaction[]) => void;
    reactions: IReaction[];
    publication: IPublication;
    username: string;
    setNewRating: (avgRating: number) => void;
}

function CommentForm({setReactions, reactions, publication, username, setNewRating}: CommentsCardProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();

    const [text, setText] = useState('');
    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(-1);

    function handleSubmit(event: any) {
        event.preventDefault();

        const reaction: IReaction = {
            id: null,
            publicationId: publication.id,
            cookUsername: username,
            rating: rating,
            comment: {
                id: null,
                content: text,
                creationDate: new Date().toISOString()
            }
        };

        if (reaction.rating === null && reaction.comment === null) {
            toast.error(t('message.noRatingOrComment'));
            return;
        }

        cookbookService.reactPublication(reaction)
            .then((response) => {
                setReactions([...reactions, response]);
                setNewRating(response.rating!);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                    toast.error(t(error.response?.data.message));
            });

    }

    function handleTextChange(event: any) {
        setText(event.target.value);
    }

    return (
        <form onSubmit={handleSubmit}
              className={"mx-auto w-1/2 border border-cook rounded flex flex-col items-stretch justify-between"}>
            <h2 className={"text-2xl"}>{t('rate&comment')}</h2>
            <div className={"flex justify-start clickable m-2 text-2xl"}>
                {[...Array(5)].map((star, index) => {
                    return (
                        <FontAwesomeIcon
                            key={index}
                            icon={faStar}
                            className={index <= hover || index < rating ? 'text-cook-light' : 'text-cook'}
                            onClick={() => setRating(index + 1)}
                            onMouseEnter={() => setHover(index)}
                            onMouseLeave={() => setHover(-1)}
                        />
                    );
                })}
            </div>

            <div className="flex justify-between items-center">
                <textarea value={text} className={"ms-2 rounded w-9/12 h-16"} onChange={handleTextChange}/>

                <button type="submit"
                        className={`border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 m-5 p-2`}>
                    {t('input.react')}
                    <FontAwesomeIcon icon={faPaperPlane} className={`ms-2`}/>
                </button>
            </div>
        </form>

    );
}

export default CommentForm;