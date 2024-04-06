import {ITrick} from "../assets/models/Publication";
import React, {useEffect} from "react";
import ReactionFooter from "./recipes/ReactionFooter";
import RecipeOptions from "./recipes/RecipeOptions";

interface ITrickCardProps {
    trick: ITrick;
    username: string;
}
function TrickCard({trick, username}: ITrickCardProps){

    return (
        <div className="border rounded-lg px-4 my-2 lg:w-3/5 w-full flex flex-col h-full justify-between">
            <div className={""}>
                <RecipeOptions username={username!} publication={trick}/>
                <h1 className="text-3xl font-semibold mt-0 pt-0">{trick.title}</h1>
                <p className="text-2xl">{trick.description}</p>

            </div>

            <footer className={"py-2"}>
                <ReactionFooter publicationTitle={trick.title}/>
            </footer>
        </div>
    );
}

export default TrickCard;