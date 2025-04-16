import { create } from 'zustand';
import {Document} from "@/components/doc/datatable/columns.tsx";

interface SelectedRowsState {
  selectedRows: Document[];
  setSelectedRows: (rows: Document[]) => void;
  clearSelectedRows: () => void;
}

export const useSelectedRowsStore = create<SelectedRowsState>((set) => ({
  selectedRows: [],
  setSelectedRows: (rows: Document[]) => set({ selectedRows: rows }),
  clearSelectedRows: () => set({ selectedRows: [] }),
}));
