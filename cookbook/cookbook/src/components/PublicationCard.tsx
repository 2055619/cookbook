import {IPublication} from "../assets/models/Publication";

interface IPublicationCardProps {
    publication: IPublication;
    username?: string;
}
function publicationCard(){

    return (
        <div>
            <h1>Publication Card</h1>
        </div>
    );
}

export default publicationCard;