import {ITrick} from "../assets/models/Publication";
import React from "react";
import PublicationOption from "./publications/PublicationOption";

interface ITrickCardProps {
    trick: ITrick;
    username: string;
}

function TrickCard({trick, username}: ITrickCardProps) {

    return (
        <div className={""}>
            <PublicationOption username={username!} publication={trick}/>
            <h1 className="text-3xl font-semibold mt-0 pt-0">{trick.title}</h1>
            <p className="text-2xl">{trick.description}</p>
        </div>
    );
}

export default TrickCard;