
import React from 'react';
import noImg from "../assets/image/noImage.jpg";

interface ImageCardProps {
    byteArray: number[];
    alt: string;
}
function ImageCard({byteArray, alt}: ImageCardProps) {
    // Convert byte array to base64 string
    const base64String = byteArray ? `data:image/jpeg;base64,${byteArray}` : null;

    return (
        <div>
            {
                base64String !== null ?
                    <img className={"w-full md:w-3/5 mx-auto"} src={base64String} alt={alt}/> :
                    <img className={"w-full md:w-3/5 mx-auto"} src={noImg} alt={"No image"}/>
            }
        </div>
    );
}

export default ImageCard;