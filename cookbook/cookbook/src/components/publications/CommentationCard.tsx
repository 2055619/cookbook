import {IComment, IPublication} from "../../assets/models/Publication";
import {useEffect, useState} from "react";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";

interface CommentsCardProps {
    publication: IPublication;
}

function CommentsCard({publication}: CommentsCardProps) {
    const cookbookService = new CookBookService();
    const [comments, setComments] = useState<IComment[]>([]);

    useEffect(() => {
        cookbookService.getCommentsByPublication(publication)
            .then((response) => {
                setComments(response);
            })
            .catch((error) => {
                toast.error(error.response?.data.message);
            });

    }, []);

    return (
        <div>
            <h1>Comments Card</h1>
            {
                comments.map((comment, index) => (
                    <div key={index}>
                        <h2>{comment.author}</h2>
                        <p>{comment.content}</p>
                    </div>
                ))
            }
        </div>
    );
}

export default CommentsCard;