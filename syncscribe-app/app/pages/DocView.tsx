import {columns, Document, documents} from "@/components/doc/datatable/columns.tsx";
import {DataTable} from "@/components/doc/datatable/datatable.tsx";
import {useEffect, useState} from "react";

async function getData(): Promise<Document[]> {
  return Promise.resolve(documents)
}

export default function Page() {
  const [data, setData] = useState<Document[]>([]);

  useEffect(() => {
    const getDocuments = async () => {
      return setData(await getData());
    }
    getDocuments();
  }, []);

  return (
    <div className="container mx-auto">
      <DataTable
        columns={columns}
        data={data}
      />
    </div>
  )
}