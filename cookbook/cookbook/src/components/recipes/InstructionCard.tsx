import React, { useState } from 'react';
import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Recipe";

interface IInstructionCardProps {
    recipe: IRecipe;
}
function InstructionCard({recipe}: IInstructionCardProps) {
    const { t } = useTranslation();
    const [currentInstructionIndex, setCurrentInstructionIndex] = useState(0);

    const handlePrevious = () => {
        setCurrentInstructionIndex(currentInstructionIndex - 1);
    };

    const handleNext = () => {
        setCurrentInstructionIndex(currentInstructionIndex + 1);
    };

    return (
        <div className="card mt-2">
            <h2 className={"text-3xl"}>{t('Instruction')}</h2>
            <p>{recipe.instructions[currentInstructionIndex]}</p>

            <div className={" mt-2"}>
                <button className={"border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 py-1 px-3 mx-10"} onClick={handlePrevious} disabled={currentInstructionIndex === 0}>
                    {t('Previous')}
                </button>
                <span>instruction # {currentInstructionIndex+1}</span>
                <button className={"border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 py-1 px-3 mx-10"} onClick={handleNext} disabled={currentInstructionIndex === recipe.instructions.length - 1}>
                    {t('Next')}
                </button>
            </div>
        </div>
    );
}

export default InstructionCard;