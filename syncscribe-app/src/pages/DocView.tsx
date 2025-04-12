import {columns, Document, documents} from "@/components/doc/datatable/columns.tsx";
import {DataTable} from "@/components/doc/datatable/datatable.tsx";
import {useEffect, useState} from "react";
import DocViewFilter from "@/components/doc/DocViewFilter.tsx";

async function getData(): Promise<Document[]> {
  return Promise.resolve(documents)
}

export default function Page() {
  const [data, setData] = useState<Document[]>([]);
  const [selectedRows, setSelectedRows] = useState<Document[]>([]);

  useEffect(() => {
    const getDocuments = async () => {
      return setData(await getData());
    }
    getDocuments();
  }, []);

  return (
    <div className="container mx-auto">
      <DocViewFilter selectedRows={selectedRows} className="mb-4" />
      <DataTable 
        columns={columns} 
        data={data} 
        onSelectionChange={setSelectedRows}
      />
    </div>
  )
}