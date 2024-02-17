import {useState} from "react";

function UnitSelector() {
  const [unit, setUnit] = useState('C');

  return (
    <div>
      <button onClick={() => setUnit('C')}>°C</button>
      <button onClick={() => setUnit('F')}>°F</button>
    </div>
  );
}