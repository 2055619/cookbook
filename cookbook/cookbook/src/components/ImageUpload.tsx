import React, {useState} from 'react';
import ImageCard from "./ImageCard";

interface ImageUploadProps {
    onImageUpload: (base64Image: number[]) => void;
}

const ImageUpload: React.FC<ImageUploadProps> = ({onImageUpload}) => {
    const [selectedImage, setSelectedImage] = useState<number[] | null>(null);

    // const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    //     const file = e.target.files?.[0];
    //     if (file) {
    //         const reader = new FileReader();
    //         reader.onloadend = () => {
    //             const base64Image = reader.result as string;
    //             setSelectedImage(base64Image);
    //             onImageUpload(base64Image);
    //         };
    //         reader.readAsDataURL(file);
    //     }
    // };

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                const arrayBuffer = reader.result as ArrayBuffer;
                const byteArray = new Uint8Array(arrayBuffer);
                const byteList = Array.from(byteArray);
                setSelectedImage(byteList);
                onImageUpload(byteList);
            };
            reader.readAsArrayBuffer(file);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleImageChange}/>

            {selectedImage && <ImageCard byteArray={selectedImage} alt={"Selected Image"} />}
            {/*{selectedImage && <img src={selectedImage} alt="Selected"/>}*/}
        </div>
    );
};

export default ImageUpload;