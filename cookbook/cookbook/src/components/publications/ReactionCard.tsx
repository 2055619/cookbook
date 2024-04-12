import {IReaction} from "../../assets/models/Publication";

interface IReactionCardProps {
    reaction: IReaction;
}

function ReactionCard({reaction}: IReactionCardProps) {

    return (
        <div className={"text-cook border border-cook"}>
            <h1>Reaction Card</h1>
        </div>
    );
}

export default ReactionCard;