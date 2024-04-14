import {IPublication, IRecipe} from "../../assets/models/Publication";
import RecipeCard from "../recipes/RecipeCard";
import TrickCard from "../TricksCard";
import ReactionFooter from "./ReactionFooter";
import React from "react";

interface IPublicationCardProps {
    publication: IPublication;
    username: string;
}

function PublicationCard({publication, username}: IPublicationCardProps) {

    function isRecipe(publication: IPublication): publication is IRecipe {
        // if (publication.publicationType === "RECIPE" && (publication as IRecipe).instructions === undefined) {
        //     console.log("Recipe: ", publication.title, (publication as IRecipe).dietTypes);
        // }
        return publication.publicationType === "RECIPE";
    }

    function isTrick(publication: IPublication): publication is IRecipe {
        return publication.publicationType === "TRICK";
    }

    return (
        <>
            <div className="border rounded-lg px-4 my-2 lg:w-3/5 w-full flex flex-col h-full justify-between">
                {
                    isRecipe(publication) ? (
                        <RecipeCard recipe={publication} username={username} />
                    ) : isTrick(publication) ? (
                        <TrickCard trick={publication} username={username} />
                    ) : (
                        <h1>Publication Not Supported</h1>
                    )
                }

                <footer className={"py-2"}>
                    <ReactionFooter publication={publication} username={username}/>
                </footer>

            </div>
        </>
    );
}

export default PublicationCard;