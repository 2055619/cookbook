import React, {useState} from 'react';

interface ImageUploadProps {
    onImageUpload: (base64Image: number[]) => void;
}

const ImageUpload: React.FC<ImageUploadProps> = ({onImageUpload}) => {
    const [selectedImage, setSelectedImage] = useState<string | null>(null);

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            const readerByte = new FileReader();
            readerByte.onloadend = () => {
                const arrayBuffer = readerByte.result as ArrayBuffer;
                const byteArray = new Uint8Array(arrayBuffer);
                const byteList = Array.from(byteArray);
                onImageUpload(byteList);
            };
            readerByte.readAsArrayBuffer(file);

            const readerImg = new FileReader();
            readerImg.onloadend = () => {
                const base64Image = readerImg.result as string;
                setSelectedImage(base64Image);
            };
            readerImg.readAsDataURL(file);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleImageChange}/>
            {selectedImage && <img className={"w-full md:w-3/5 mx-auto"} src={selectedImage} alt="Selected"/>}
        </div>
    );
};

export default ImageUpload;