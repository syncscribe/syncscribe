"use client"

import {HardDrive, Home, Star, Trash2Icon, Users,} from "lucide-react"
import {NavMain} from "@/components/nav/NavMain.tsx"
import {NavUser} from "@/components/nav/NavUser.tsx"
import {Logo} from "@/components/nav/Logo.tsx"
import {Sidebar, SidebarContent, SidebarFooter, SidebarHeader, SidebarRail,} from "@/components/ui/sidebar.tsx"
import {NavLabels} from "@/components/nav/NavLabels.tsx";

const data = {
  main: [
    {
      name: "Home",
      url: "/home",
      icon: Home,
    },
    {
      name: "My Documents",
      url: "/home/documents",
      icon: HardDrive,
    },
    {
      name: "Shared",
      url: "/home/documents?filter=shared",
      icon: Users,
    },
    {
      name: "Starred",
      url: "/home/documents?filter=starred",
      icon: Star,
    },
    {
      name: "Recycle bin",
      url: "/home/documents?filter=trash",
      icon: Trash2Icon,
    },
  ],
}

const labels = [
  {
    name: "Architecture",
    color: '#0042aa'
  },
  {
    name: "Personal",
    color: '#b51a00'
  }
]

type SidebarProps = {
  signout: () => void
}

export function AppSidebar({signout}: SidebarProps) {
  return (
    <Sidebar collapsible="icon">
      <SidebarHeader>
        <Logo/>
      </SidebarHeader>
      <SidebarContent>
        <NavMain projects={data.main}/>
        <NavLabels labels={labels}/>
      </SidebarContent>
      <SidebarFooter>
        <NavUser signout={signout}/>
      </SidebarFooter>
      <SidebarRail/>
    </Sidebar>
  )
}
