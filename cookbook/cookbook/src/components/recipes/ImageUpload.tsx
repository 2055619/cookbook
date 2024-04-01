import React, { useState } from 'react';

interface ImageUploadProps {
    onImageUpload: (base64Image: string) => void;
}

const ImageUpload: React.FC<ImageUploadProps> = ({ onImageUpload }) => {
    const [selectedImage, setSelectedImage] = useState<string | null>(null);

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                const base64Image = reader.result as string;
                setSelectedImage(base64Image);
                onImageUpload(base64Image);
            };
            reader.readAsDataURL(file);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleImageChange} />
            {selectedImage && <img src={selectedImage} alt="Selected" />}
        </div>
    );
};

export default ImageUpload;