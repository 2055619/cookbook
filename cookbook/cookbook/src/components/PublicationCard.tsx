import {IPublication, IRecipe} from "../assets/models/Publication";
import RecipeCard from "./recipes/RecipeCard";
import TrickCard from "./TricksCard";

interface IPublicationCardProps {
    publication: IPublication;
    username: string;
}

function PublicationCard({publication, username}: IPublicationCardProps) {

    function isRecipe(publication: IPublication): publication is IRecipe {
        return (publication as IRecipe).instructions !== undefined;
    }

    function isTrick(publication: IPublication): publication is IRecipe {
        return (publication as IRecipe).instructions === undefined;
    }

    return (
        <>
            {
                isRecipe(publication) ? (
                    RecipeCard({recipe: publication, username: username})
                ) : isTrick(publication) ? (
                    TrickCard({trick: publication, username: username})
                ) : (
                    <>
                        <h1>Publication Not Supported</h1>
                    </>
                )
            }
        </>
    );
}

export default PublicationCard;