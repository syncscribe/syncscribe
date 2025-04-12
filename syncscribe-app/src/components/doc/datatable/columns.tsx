import {ColumnDef} from "@tanstack/react-table";
import {Button} from "@/components/ui/button.tsx";
import {ChevronDown, ChevronsUpDown, ChevronUp, FileText, Folder, UsersRound} from "lucide-react";
import {Link} from "react-router-dom";
import {Checkbox} from "@/components/ui/checkbox.tsx";

export type Document = {
  id: string;
  name: string;
  sharing: boolean;
  fileSizeInKB: number;
  fileType: string;
  createdAt: string;
  createdBy: string;
  updatedAt: string;
  updatedBy: string;
}

export const columns: ColumnDef<Document>[] = [
  {
    id: "select",
    header: ({table}) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() ||
          (table.getIsSomePageRowsSelected() && "indeterminate")
        }
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({row}) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
  },
  {
    accessorKey: "name", header: ({column}) => {
      return (
        <div>
          <Button
            variant="ghost"
            className={"cursor-pointer"}
            onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
          >
            Name
            {column.getIsSorted() === "asc" && <ChevronUp/>}
            {column.getIsSorted() === "desc" && <ChevronDown/>}
            {!column.getIsSorted() && <ChevronsUpDown/>}
          </Button>
        </div>
      )
    },
    cell: ({row}) => {
      const name = row.original.name;
      const fileType = row.original.fileType;
      const id = row.original.id;
      if (fileType === "docx") {
        return <div className={"flex flex-row items-center gap-2"}>
          <FileText strokeWidth={1.25} size={20} color="#0056d6"/>
          <Link to={`/editor/${id}`} target={"_blank"} className={"cursor-pointer hover:underline"}>{name}</Link>
        </div>
      } else if (fileType === "directory") {
        return <div className={"flex flex-row items-center gap-2"}>
          <Folder strokeWidth={1.5} size={20} color="#d29d00"/>
          <Link to={`/documents?root=${id}`} className={"cursor-pointer hover:underline"}>{name}</Link>
        </div>
      } else {
        return <div>{name}</div>
      }
    },
  },
  {
    accessorKey: "fileSizeInKB", header: ({column}) => {
      return (
        <Button
          variant="ghost"
          className={"cursor-pointer"}
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Size
          {column.getIsSorted() === "asc" && <ChevronUp/>}
          {column.getIsSorted() === "desc" && <ChevronDown/>}
          {!column.getIsSorted() && <ChevronsUpDown/>}
        </Button>
      )
    }, cell: ({row}) => {
      const fileSize = row.original.fileSizeInKB;
      const fileType = row.original.fileType;
      if (fileType !== "directory") {
        return <div>{fileSize} KB</div>
      }
      return <></>
    },
  },
  {
    accessorKey: "sharing", header: "Sharing",
    cell: ({row}) => {
      const sharing = row.original.sharing;
      if (sharing) {
        return <div className={"flex flex-row items-center gap-1"}>
          <UsersRound strokeWidth={1.5} size={20}/>Shared
        </div>;
      } else {
        return <div>Private</div>;
      }
    },
  },
  {accessorKey: "createdAt", header: "Created at"},
  {
    accessorKey: "updatedAt", header: ({column}) => {
      return (
        <Button
          variant="ghost"
          className={"cursor-pointer"}
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Updated at
          {column.getIsSorted() === "asc" && <ChevronUp/>}
          {column.getIsSorted() === "desc" && <ChevronDown/>}
          {!column.getIsSorted() && <ChevronsUpDown/>}
        </Button>
      )
    },
  },
  {accessorKey: "updatedBy", header: "Updated By"},
];

export const documents: Document[] = [{
  "id": "236de987-ad57-457e-8db2-bd357efc1fce",
  "name": "PrimisIn",
  "sharing": false,
  "fileSizeInKB": 787,
  "fileType": "directory",
  "createdAt": "5/30/2024",
  "createdBy": "Lanie Tupper",
  "updatedAt": "6/20/2024",
  "updatedBy": "Ninetta Hendren"
},
  {
    "id": "00465f13-c4a7-4082-b631-8b4736303492",
    "name": "RidiculusMusEtiam",
    "sharing": false,
    "fileSizeInKB": 209,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Sabra Mothersdale",
    "updatedAt": "6/20/2024",
    "updatedBy": "Gregorius Hazel"
  },
  {
    "id": "111e7929-84fa-4afd-ac3c-43fa39626bdd",
    "name": "Erat.docx",
    "sharing": true,
    "fileSizeInKB": 23,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Anjela Pays",
    "updatedAt": "6/20/2024",
    "updatedBy": "Oralie Burges"
  },
  {
    "id": "05e0248e-72b0-4b27-b234-9be0efd84adf",
    "name": "Nisl.docx",
    "sharing": false,
    "fileSizeInKB": 636,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Stefan Killock",
    "updatedAt": "6/20/2024",
    "updatedBy": "Stepha Attac"
  },
  {
    "id": "565c14f0-8949-464c-83dd-d3bfe3a1d3c4",
    "name": "SitAmetConsectetuer.docx",
    "sharing": true,
    "fileSizeInKB": 772,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Gustave Rennison",
    "updatedAt": "6/20/2024",
    "updatedBy": "Sibilla Mault"
  },
  {
    "id": "a60ec94c-2770-4b53-b76d-27102bcbee14",
    "name": "Morbi.docx",
    "sharing": false,
    "fileSizeInKB": 460,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Carling Willcox",
    "updatedAt": "6/20/2024",
    "updatedBy": "Bartolemo Baylay"
  },
  {
    "id": "3fb77748-7d50-4c68-8fa3-14b52f69f02f",
    "name": "FaucibusAccumsan.docx",
    "sharing": true,
    "fileSizeInKB": 217,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Riley Novakovic",
    "updatedAt": "6/20/2024",
    "updatedBy": "Floria Greenhow"
  },
  {
    "id": "62662b77-3441-4ce3-91c1-e39b884f5916",
    "name": "Habitasse.docx",
    "sharing": true,
    "fileSizeInKB": 28,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Jehanna Anstice",
    "updatedAt": "6/20/2024",
    "updatedBy": "Libbie Cleatherow"
  },
  {
    "id": "4f32cf1d-f213-42e1-bc9d-6dcf4f5f4432",
    "name": "EuEstCongue.docx",
    "sharing": true,
    "fileSizeInKB": 954,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Mozelle Rannald",
    "updatedAt": "6/20/2024",
    "updatedBy": "Bartholomeus Daniaud"
  },
  {
    "id": "f36fd904-d6d3-4db2-89ac-1a15a749e817",
    "name": "Aliquam",
    "sharing": true,
    "fileSizeInKB": 779,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Elena Craghead",
    "updatedAt": "6/20/2024",
    "updatedBy": "Lora Summersett"
  },
  {
    "id": "d3b1c364-abdc-48d5-9a8f-b553b284ce80",
    "name": "TinciduntEgetTempus.docx",
    "sharing": false,
    "fileSizeInKB": 606,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Domingo Bart",
    "updatedAt": "6/20/2024",
    "updatedBy": "Adelaide Dodell"
  },
  {
    "id": "a82b24f9-719c-4cbe-8e1a-98f69d75fc98",
    "name": "NislDuis.docx",
    "sharing": true,
    "fileSizeInKB": 473,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Delaney de Savery",
    "updatedAt": "6/20/2024",
    "updatedBy": "Bertrando Kincaid"
  },
  {
    "id": "93a105af-feef-4fd2-8504-d9ae5285ada7",
    "name": "Id",
    "sharing": false,
    "fileSizeInKB": 588,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Cassy Marioneau",
    "updatedAt": "6/20/2024",
    "updatedBy": "Sonni McCrea"
  },
  {
    "id": "db31a3a4-3cc5-4cda-bbf6-83e88c4e8f16",
    "name": "AmetEleifendPede.docx",
    "sharing": true,
    "fileSizeInKB": 675,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Codee Crambie",
    "updatedAt": "6/20/2024",
    "updatedBy": "Codi Sains"
  },
  {
    "id": "2e82538a-a76f-459c-a0c0-4a591ca996e6",
    "name": "NecNisi",
    "sharing": false,
    "fileSizeInKB": 925,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Faye Steadman",
    "updatedAt": "6/20/2024",
    "updatedBy": "Maxy Archibold"
  },
  {
    "id": "94075dcd-1cfd-43b5-adbf-8c8a3420aff0",
    "name": "InMagnaBibendum",
    "sharing": false,
    "fileSizeInKB": 899,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Charlene McMorran",
    "updatedAt": "6/20/2024",
    "updatedBy": "Elvera Milward"
  },
  {
    "id": "a5e29e32-7318-43e2-9721-5dee90e30633",
    "name": "ConvallisNunc.docx",
    "sharing": false,
    "fileSizeInKB": 905,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Cherida de Leon",
    "updatedAt": "6/20/2024",
    "updatedBy": "Rozele Goodall"
  },
  {
    "id": "782a67fb-f849-45f6-b088-0770c94cdb0b",
    "name": "LuctusCumSociis.docx",
    "sharing": true,
    "fileSizeInKB": 943,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Wolfgang Bellie",
    "updatedAt": "6/20/2024",
    "updatedBy": "Winnie Haken"
  },
  {
    "id": "79795042-2f9b-4748-9fe8-22d38a1db4ea",
    "name": "Porttitor.docx",
    "sharing": true,
    "fileSizeInKB": 781,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Arda Treneman",
    "updatedAt": "6/20/2024",
    "updatedBy": "Irving Bachman"
  },
  {
    "id": "2eaa3ef9-0090-4650-977b-d7b49637ca4d",
    "name": "Pellentesque.docx",
    "sharing": true,
    "fileSizeInKB": 792,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Diane-marie Gramer",
    "updatedAt": "6/20/2024",
    "updatedBy": "Zsazsa Trow"
  },
  {
    "id": "649d595d-9b6a-4e71-8b79-3cf2b39c0b1a",
    "name": "Mauris.docx",
    "sharing": true,
    "fileSizeInKB": 944,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Nertie Provis",
    "updatedAt": "6/20/2024",
    "updatedBy": "Juanita Mordanti"
  },
  {
    "id": "2e566367-6913-4437-abdb-7bacb5e4555e",
    "name": "AtTurpisDonec",
    "sharing": true,
    "fileSizeInKB": 851,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Woodie Beaumont",
    "updatedAt": "6/20/2024",
    "updatedBy": "Korey Curley"
  },
  {
    "id": "596bd2e5-c6a2-462e-8abf-f89e2ec158b8",
    "name": "PotentiInEleifend.docx",
    "sharing": false,
    "fileSizeInKB": 738,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Brucie Moles",
    "updatedAt": "6/20/2024",
    "updatedBy": "Winonah Pellissier"
  },
  {
    "id": "cbeaf01b-06c0-4232-ae0c-4a47348dc219",
    "name": "PulvinarSed",
    "sharing": false,
    "fileSizeInKB": 289,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Bambi Wandrack",
    "updatedAt": "6/20/2024",
    "updatedBy": "Estele Arnett"
  },
  {
    "id": "fc7f0eec-0384-4873-a10b-3784fc6b14b5",
    "name": "Nulla",
    "sharing": false,
    "fileSizeInKB": 884,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Ganny Gathercoal",
    "updatedAt": "6/20/2024",
    "updatedBy": "Leanor Carnihan"
  },
  {
    "id": "a8c04098-2693-4e5d-93d8-40ac59fc9462",
    "name": "NuncNisl",
    "sharing": true,
    "fileSizeInKB": 334,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Anett Theuss",
    "updatedAt": "6/20/2024",
    "updatedBy": "Davida Wickenden"
  },
  {
    "id": "1b36d2ae-afff-4309-b19e-cee97c94c5de",
    "name": "Donec.docx",
    "sharing": true,
    "fileSizeInKB": 1,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Breena Leask",
    "updatedAt": "6/20/2024",
    "updatedBy": "Ody Stoyles"
  },
  {
    "id": "e84f49f4-938d-4281-ad93-6198a10548b2",
    "name": "A.docx",
    "sharing": true,
    "fileSizeInKB": 908,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Seumas Yve",
    "updatedAt": "6/20/2024",
    "updatedBy": "Woodman Firk"
  },
  {
    "id": "0135f1d1-95dc-41d1-aff7-24daac035f4c",
    "name": "Ut",
    "sharing": false,
    "fileSizeInKB": 284,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Gabriello De Fraine",
    "updatedAt": "6/20/2024",
    "updatedBy": "Gretta Rummin"
  },
  {
    "id": "14232def-ceb5-4eb3-9da2-c67acaa6ad96",
    "name": "DolorVelEst",
    "sharing": false,
    "fileSizeInKB": 38,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Bertie Byles",
    "updatedAt": "6/20/2024",
    "updatedBy": "Nester Ilem"
  },
  {
    "id": "69d9e875-b207-491b-948a-026883d9afb8",
    "name": "SapienCursus",
    "sharing": true,
    "fileSizeInKB": 272,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Chantal Berni",
    "updatedAt": "6/20/2024",
    "updatedBy": "Lucho Cowp"
  },
  {
    "id": "495550da-d296-423b-883b-c3a45d7fe5c6",
    "name": "NullaSedVel",
    "sharing": true,
    "fileSizeInKB": 224,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Codie Kinchin",
    "updatedAt": "6/20/2024",
    "updatedBy": "Evangelin McGoldrick"
  },
  {
    "id": "e542158c-9767-44f4-837d-8309e2e9c894",
    "name": "DiamNeque",
    "sharing": true,
    "fileSizeInKB": 662,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Jessika McFeat",
    "updatedAt": "6/20/2024",
    "updatedBy": "Irene O'Hickee"
  },
  {
    "id": "f4909394-5e3a-4a53-b1f1-7a5e4e306a07",
    "name": "AmetJustoMorbi",
    "sharing": false,
    "fileSizeInKB": 802,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Myriam Dorey",
    "updatedAt": "6/20/2024",
    "updatedBy": "Prudence Breem"
  },
  {
    "id": "f4355c44-9f42-48c1-a166-16155d02bafe",
    "name": "PosuereCubilia",
    "sharing": true,
    "fileSizeInKB": 378,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Seka Deetch",
    "updatedAt": "6/20/2024",
    "updatedBy": "Huberto Bebis"
  },
  {
    "id": "845f172c-c933-4e3b-bcce-07a683c3e7db",
    "name": "VolutpatConvallis.docx",
    "sharing": false,
    "fileSizeInKB": 466,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Keven Darlaston",
    "updatedAt": "6/20/2024",
    "updatedBy": "See Ewence"
  },
  {
    "id": "59c76fe5-f760-4d3c-9c98-5a27bf9c9dcd",
    "name": "DapibusDuis.docx",
    "sharing": true,
    "fileSizeInKB": 322,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Shurlocke Brunet",
    "updatedAt": "6/20/2024",
    "updatedBy": "Carma Kopps"
  },
  {
    "id": "cd5d627a-e88d-4ce9-b175-c8bd76aadc67",
    "name": "LacusCurabitur.docx",
    "sharing": false,
    "fileSizeInKB": 688,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Ardelis Tregensoe",
    "updatedAt": "6/20/2024",
    "updatedBy": "Adam Sleany"
  },
  {
    "id": "2791725a-679f-4697-bcf5-9bb1a687dcb9",
    "name": "Vel.docx",
    "sharing": false,
    "fileSizeInKB": 436,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Tailor Dorkens",
    "updatedAt": "6/20/2024",
    "updatedBy": "Emery Burel"
  },
  {
    "id": "4635198f-b03a-4e5b-926c-86682daeb009",
    "name": "PlateaDictumst.docx",
    "sharing": true,
    "fileSizeInKB": 346,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Rozele Littrick",
    "updatedAt": "6/20/2024",
    "updatedBy": "Lotte Maxsted"
  },
  {
    "id": "09165e71-07e3-4b49-8f7f-2c54b6c8b966",
    "name": "VulputateUtUltrices",
    "sharing": true,
    "fileSizeInKB": 70,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Swen Moorcraft",
    "updatedAt": "6/20/2024",
    "updatedBy": "Esma McVeagh"
  },
  {
    "id": "5119af1a-a4bc-42fc-9780-d2ab09dc5479",
    "name": "EnimLeoRhoncus.docx",
    "sharing": false,
    "fileSizeInKB": 775,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Kira Neller",
    "updatedAt": "6/20/2024",
    "updatedBy": "Calla Chomiszewski"
  },
  {
    "id": "fbaec093-c07b-40d4-953d-3546e5bc3c4e",
    "name": "Ligula",
    "sharing": false,
    "fileSizeInKB": 964,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Noelyn Winters",
    "updatedAt": "6/20/2024",
    "updatedBy": "Terry Limbourne"
  },
  {
    "id": "9b307739-a35b-40df-bb95-127bd4c013c2",
    "name": "SedTristiqueIn.docx",
    "sharing": false,
    "fileSizeInKB": 172,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Elysha Ahlf",
    "updatedAt": "6/20/2024",
    "updatedBy": "Nissie Treharne"
  },
  {
    "id": "a6af44b8-e556-4161-8682-362e56d1e9e4",
    "name": "Habitasse",
    "sharing": true,
    "fileSizeInKB": 943,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Verena Twidell",
    "updatedAt": "6/20/2024",
    "updatedBy": "Rourke Ambrogelli"
  },
  {
    "id": "905c15ff-4cb1-4516-baac-301cf2429206",
    "name": "PretiumIaculis",
    "sharing": false,
    "fileSizeInKB": 326,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Gilly Carnaman",
    "updatedAt": "6/20/2024",
    "updatedBy": "Emelina Borgars"
  },
  {
    "id": "828be2b9-1362-4778-b007-94ba82c2b961",
    "name": "TurpisSed",
    "sharing": false,
    "fileSizeInKB": 76,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Auroora Chappell",
    "updatedAt": "6/20/2024",
    "updatedBy": "Turner Keningley"
  },
  {
    "id": "75fc6324-7b87-476f-9c90-94edbdccd906",
    "name": "NuncDonec",
    "sharing": true,
    "fileSizeInKB": 938,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Orsa Huck",
    "updatedAt": "6/20/2024",
    "updatedBy": "Nial Odom"
  },
  {
    "id": "c21b8393-cb80-4f7f-8274-bb6c8b654a37",
    "name": "Nam",
    "sharing": false,
    "fileSizeInKB": 794,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Tracy Mustoe",
    "updatedAt": "6/20/2024",
    "updatedBy": "Meredeth Dressel"
  },
  {
    "id": "10021a3a-54be-4edd-8c24-4c0f8101ef2a",
    "name": "OdioElementum",
    "sharing": false,
    "fileSizeInKB": 676,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Abram Oakeby",
    "updatedAt": "6/20/2024",
    "updatedBy": "Gusta Filisov"
  },
  {
    "id": "beb42c7a-d460-494e-8b6f-097bdbf95bb2",
    "name": "QuisLibero",
    "sharing": true,
    "fileSizeInKB": 365,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Allison Kares",
    "updatedAt": "6/20/2024",
    "updatedBy": "Garrott Guly"
  },
  {
    "id": "61de6186-4f15-484b-b2df-23dc6c08a670",
    "name": "NonMattisPulvinar",
    "sharing": false,
    "fileSizeInKB": 379,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Sherill Stearnes",
    "updatedAt": "6/20/2024",
    "updatedBy": "Gabbi Marcroft"
  },
  {
    "id": "84408361-c366-4136-aae8-7b44dc623810",
    "name": "Nec.docx",
    "sharing": true,
    "fileSizeInKB": 312,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Cyril Iannuzzelli",
    "updatedAt": "6/20/2024",
    "updatedBy": "Kahlil Roz"
  },
  {
    "id": "69029938-50a0-448f-ab9c-879a49284293",
    "name": "Posuere.docx",
    "sharing": true,
    "fileSizeInKB": 528,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Nollie Rallings",
    "updatedAt": "6/20/2024",
    "updatedBy": "Viviene Bowes"
  },
  {
    "id": "748f459e-7fff-4e73-9008-ecca74ca45b8",
    "name": "Id",
    "sharing": true,
    "fileSizeInKB": 153,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Ike Pauer",
    "updatedAt": "6/20/2024",
    "updatedBy": "Sondra Aim"
  },
  {
    "id": "5bf9db04-b9ee-4fc8-a12e-3467bd84d0a5",
    "name": "NullaSuscipitLigula",
    "sharing": true,
    "fileSizeInKB": 745,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Meade Montrose",
    "updatedAt": "6/20/2024",
    "updatedBy": "Burk Blaase"
  },
  {
    "id": "f260c012-f3d5-48b4-a4b1-f4b5e75fea2c",
    "name": "Erat.docx",
    "sharing": true,
    "fileSizeInKB": 75,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Avrit Shortland",
    "updatedAt": "6/20/2024",
    "updatedBy": "Ardeen Rodrig"
  },
  {
    "id": "b68de263-1a4b-49cd-a8c6-59ef523d67c5",
    "name": "AcNullaSed",
    "sharing": false,
    "fileSizeInKB": 634,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Barby Pryor",
    "updatedAt": "6/20/2024",
    "updatedBy": "Lenard Sapshed"
  },
  {
    "id": "4e45b88a-fff5-49f2-99ef-a18e17290a29",
    "name": "LiberoNon",
    "sharing": false,
    "fileSizeInKB": 894,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Kermie Bossel",
    "updatedAt": "6/20/2024",
    "updatedBy": "Deonne Depka"
  },
  {
    "id": "76244062-512b-43a6-830f-b58ae4159f5f",
    "name": "Congue.docx",
    "sharing": true,
    "fileSizeInKB": 386,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Roxanna Riddick",
    "updatedAt": "6/20/2024",
    "updatedBy": "Deerdre Lumox"
  },
  {
    "id": "8b959c3d-8e53-4966-866e-a6b74efb3a5e",
    "name": "IpsumPrimis",
    "sharing": true,
    "fileSizeInKB": 779,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Frasquito Harling",
    "updatedAt": "6/20/2024",
    "updatedBy": "Robbin Brunroth"
  },
  {
    "id": "b1d20f5a-36c7-4abb-9d41-ab70f7aab237",
    "name": "MollisMolestieLorem.docx",
    "sharing": true,
    "fileSizeInKB": 193,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Jerrilee Erridge",
    "updatedAt": "6/20/2024",
    "updatedBy": "Jennilee Batrop"
  },
  {
    "id": "c76aa45a-95d3-4dac-bc42-6681799def8a",
    "name": "NonLectusAliquam",
    "sharing": false,
    "fileSizeInKB": 961,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Karl Wyburn",
    "updatedAt": "6/20/2024",
    "updatedBy": "Sherilyn McMearty"
  },
  {
    "id": "6f049283-3802-49e2-8b4e-2c13f0ae18d9",
    "name": "EstCongueElementum",
    "sharing": false,
    "fileSizeInKB": 142,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Gerome Tatford",
    "updatedAt": "6/20/2024",
    "updatedBy": "Gerard Shapiro"
  },
  {
    "id": "40e1a875-0b3a-4af6-9bf5-49811dec67ae",
    "name": "IntegerAcNeque",
    "sharing": false,
    "fileSizeInKB": 795,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Marielle Parradine",
    "updatedAt": "6/20/2024",
    "updatedBy": "Derek Elgram"
  },
  {
    "id": "3a69244a-af43-47e1-8db2-1d130cf92cfe",
    "name": "Suspendisse.docx",
    "sharing": false,
    "fileSizeInKB": 508,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Johnna Rubinov",
    "updatedAt": "6/20/2024",
    "updatedBy": "Elvin Helder"
  },
  {
    "id": "8d6b8a00-1735-4987-8b1b-7f3f7c19a23e",
    "name": "At",
    "sharing": true,
    "fileSizeInKB": 27,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Hyacintha Oughton",
    "updatedAt": "6/20/2024",
    "updatedBy": "Dodi Espinosa"
  },
  {
    "id": "3dd2cc46-d410-46ce-b082-6dae2dd3ed46",
    "name": "Consequat.docx",
    "sharing": true,
    "fileSizeInKB": 624,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Darlene Maxwale",
    "updatedAt": "6/20/2024",
    "updatedBy": "Brinn Pestridge"
  },
  {
    "id": "dccc9e2c-ea38-4bff-b72b-90bfd938a2eb",
    "name": "Eget",
    "sharing": true,
    "fileSizeInKB": 215,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Beatrix Kerrey",
    "updatedAt": "6/20/2024",
    "updatedBy": "Bernelle Reeders"
  },
  {
    "id": "54e6e64c-eade-4c34-8b59-81f19458b4da",
    "name": "EstLacinia",
    "sharing": false,
    "fileSizeInKB": 857,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Athena Roome",
    "updatedAt": "6/20/2024",
    "updatedBy": "Cherise O'Lagene"
  },
  {
    "id": "4f91e0c7-41ac-4849-87f0-aeee65877b38",
    "name": "NatoquePenatibus.docx",
    "sharing": false,
    "fileSizeInKB": 14,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Minerva McMurty",
    "updatedAt": "6/20/2024",
    "updatedBy": "Everett Linch"
  },
  {
    "id": "7bbe767a-7e6b-4517-b906-75abef79c55b",
    "name": "AtTurpis.docx",
    "sharing": true,
    "fileSizeInKB": 763,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Rani Stodit",
    "updatedAt": "6/20/2024",
    "updatedBy": "Deirdre Cranston"
  },
  {
    "id": "efc7153f-a3a1-494e-865c-d0155d38b1a8",
    "name": "DuisConsequat",
    "sharing": true,
    "fileSizeInKB": 420,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Brock Mundie",
    "updatedAt": "6/20/2024",
    "updatedBy": "Opalina Calladine"
  },
  {
    "id": "0b11af65-2c04-4864-adf7-b322c0740ee3",
    "name": "QuamNecDui.docx",
    "sharing": false,
    "fileSizeInKB": 983,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Clari Arcase",
    "updatedAt": "6/20/2024",
    "updatedBy": "Cooper Arnoult"
  },
  {
    "id": "fa9e3935-b187-46a1-a151-eca9f56db0f9",
    "name": "Commodo.docx",
    "sharing": false,
    "fileSizeInKB": 86,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Hulda Piken",
    "updatedAt": "6/20/2024",
    "updatedBy": "Bertram Krzyzowski"
  },
  {
    "id": "26037030-f46b-4c01-8399-a12b69039111",
    "name": "InTempus.docx",
    "sharing": false,
    "fileSizeInKB": 253,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Hally Piggrem",
    "updatedAt": "6/20/2024",
    "updatedBy": "Rodrigo Crabbe"
  },
  {
    "id": "d6c27132-1926-4ef0-bb9b-92635b65cea2",
    "name": "EnimIn.docx",
    "sharing": true,
    "fileSizeInKB": 586,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Derril Ferneley",
    "updatedAt": "6/20/2024",
    "updatedBy": "Dulcie Pollicatt"
  },
  {
    "id": "6017d961-aadd-43f3-a1fa-6e609450dd63",
    "name": "IaculisJusto.docx",
    "sharing": true,
    "fileSizeInKB": 81,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Gelya Smogur",
    "updatedAt": "6/20/2024",
    "updatedBy": "Charity Blackster"
  },
  {
    "id": "87711fcb-0533-4910-a177-2ee79197678d",
    "name": "AcEnimIn.docx",
    "sharing": false,
    "fileSizeInKB": 310,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Rustin Yvens",
    "updatedAt": "6/20/2024",
    "updatedBy": "Thalia Bullingham"
  },
  {
    "id": "10c81382-6823-437b-9ae6-e61f17845b8e",
    "name": "MaecenasLeo.docx",
    "sharing": true,
    "fileSizeInKB": 823,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Murielle Master",
    "updatedAt": "6/20/2024",
    "updatedBy": "Karry Biskup"
  },
  {
    "id": "c4e729b0-5261-4ea0-ad51-eaf044ac4191",
    "name": "Aliquam.docx",
    "sharing": true,
    "fileSizeInKB": 251,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Keen Pietasch",
    "updatedAt": "6/20/2024",
    "updatedBy": "Bea Novis"
  },
  {
    "id": "51e52350-9ba3-4414-8efd-dbf3cc89fa70",
    "name": "Rutrum",
    "sharing": false,
    "fileSizeInKB": 909,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Morry Philot",
    "updatedAt": "6/20/2024",
    "updatedBy": "Brodie Rozier"
  },
  {
    "id": "f69fbe72-d884-459d-91a1-faf65e3283db",
    "name": "Hac",
    "sharing": true,
    "fileSizeInKB": 275,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Barnett Hollingshead",
    "updatedAt": "6/20/2024",
    "updatedBy": "Shell Tunnadine"
  },
  {
    "id": "ebd6f174-cb0a-49e3-932c-b66efb847d75",
    "name": "Dui",
    "sharing": false,
    "fileSizeInKB": 828,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Moss Kerans",
    "updatedAt": "6/20/2024",
    "updatedBy": "Emmy Dairton"
  },
  {
    "id": "cccac69c-fe6c-4eb7-aa26-0b3bf972bd21",
    "name": "NequeSapienPlacerat.docx",
    "sharing": true,
    "fileSizeInKB": 309,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Elena Heller",
    "updatedAt": "6/20/2024",
    "updatedBy": "Kissiah Birkenshaw"
  },
  {
    "id": "3e74e84a-af0c-410c-9ae0-f66d465568fa",
    "name": "VolutpatConvallisMorbi",
    "sharing": false,
    "fileSizeInKB": 858,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Harold Crumley",
    "updatedAt": "6/20/2024",
    "updatedBy": "Debi Eglese"
  },
  {
    "id": "731a29bc-3383-4e6d-9349-a4809396033a",
    "name": "PosuereFelisSed",
    "sharing": false,
    "fileSizeInKB": 489,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Fidelia Norker",
    "updatedAt": "6/20/2024",
    "updatedBy": "Ulick Khan"
  },
  {
    "id": "b8cbd848-c0c2-4a4a-88f6-bace37cd6cfd",
    "name": "MorbiUt",
    "sharing": false,
    "fileSizeInKB": 173,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Glynda Iveans",
    "updatedAt": "6/20/2024",
    "updatedBy": "Arlette Letixier"
  },
  {
    "id": "34b5f845-8b96-41b4-b1da-9173cbfbc4a1",
    "name": "OdioCurabiturConvallis",
    "sharing": true,
    "fileSizeInKB": 816,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Forrester Stendell",
    "updatedAt": "6/20/2024",
    "updatedBy": "Broderick De Hoogh"
  },
  {
    "id": "b9b2fc09-6fe9-4e5d-a9be-a3c6b2b3b8c0",
    "name": "Quam",
    "sharing": true,
    "fileSizeInKB": 505,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Iormina Natt",
    "updatedAt": "6/20/2024",
    "updatedBy": "Nichole Clemon"
  },
  {
    "id": "c5dd3944-6294-4ffb-99c8-32d64da6625d",
    "name": "PhasellusSitAmet",
    "sharing": true,
    "fileSizeInKB": 216,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Jefferey Guyton",
    "updatedAt": "6/20/2024",
    "updatedBy": "Perrine Martinyuk"
  },
  {
    "id": "ddc512a0-5951-44e3-bcfe-5d7324c59fb6",
    "name": "Quisque.docx",
    "sharing": true,
    "fileSizeInKB": 78,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Clarette Passo",
    "updatedAt": "6/20/2024",
    "updatedBy": "Mariette Fritschel"
  },
  {
    "id": "6b6aec29-6005-49c9-969f-504f900f52c8",
    "name": "Nulla",
    "sharing": true,
    "fileSizeInKB": 118,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Darby Cours",
    "updatedAt": "6/20/2024",
    "updatedBy": "Harwell Lang"
  },
  {
    "id": "601be9a2-251c-4ecb-a3cf-62df962510e4",
    "name": "NamNulla",
    "sharing": false,
    "fileSizeInKB": 17,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Terrence Stallwood",
    "updatedAt": "6/20/2024",
    "updatedBy": "Kirsti Kwietak"
  },
  {
    "id": "1c405e69-d15e-4b14-9ef6-681dc305236c",
    "name": "Ante",
    "sharing": false,
    "fileSizeInKB": 147,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Lovell Laborde",
    "updatedAt": "6/20/2024",
    "updatedBy": "Donavon Pengelley"
  },
  {
    "id": "87965b9f-181d-4aca-8af2-be36c487da29",
    "name": "LaciniaAeneanSit.docx",
    "sharing": true,
    "fileSizeInKB": 819,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Oriana Haggis",
    "updatedAt": "6/20/2024",
    "updatedBy": "Darsey Finney"
  },
  {
    "id": "c4f6c586-51c9-4990-9590-c39cbbf39c26",
    "name": "JustoMaecenasRhoncus",
    "sharing": false,
    "fileSizeInKB": 633,
    "fileType": "directory",
    "createdAt": "5/30/2024",
    "createdBy": "Alva Foakes",
    "updatedAt": "6/20/2024",
    "updatedBy": "Brod Shambrook"
  },
  {
    "id": "f7978197-65c9-441e-b36f-9fde74388c38",
    "name": "Non.docx",
    "sharing": false,
    "fileSizeInKB": 93,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Thelma MacCostye",
    "updatedAt": "6/20/2024",
    "updatedBy": "Zia Charley"
  },
  {
    "id": "c9bb1800-a2b2-4db7-b46f-01deb8453c5a",
    "name": "MorbiSemMauris.docx",
    "sharing": false,
    "fileSizeInKB": 808,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Desdemona Rankine",
    "updatedAt": "6/20/2024",
    "updatedBy": "Danie Lawty"
  },
  {
    "id": "c422552a-691e-4699-8cc3-7e5f0c635015",
    "name": "At.docx",
    "sharing": true,
    "fileSizeInKB": 19,
    "fileType": "docx",
    "createdAt": "5/30/2024",
    "createdBy": "Clyve Measen",
    "updatedAt": "6/20/2024",
    "updatedBy": "Vinny De Lascy"
  }];