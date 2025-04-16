"use client"

import * as React from "react"
import {HardDrive, Home, Star, Trash2Icon, Users,} from "lucide-react"
import {NavMain} from "@/components/nav/NavMain.tsx"
import {NavUser} from "@/components/nav/NavUser.tsx"
import {Logo} from "@/components/nav/Logo.tsx"
import {Sidebar, SidebarContent, SidebarFooter, SidebarHeader, SidebarRail,} from "@/components/ui/sidebar.tsx"
import {NavLabels} from "@/components/nav/NavLabels.tsx";

const data = {
  user: {
    name: "Hoang Nguyen",
    email: "hoangna1204@gmail.com",
  },
  main: [
    {
      name: "Home",
      url: "/",
      icon: Home,
    },
    {
      name: "My Documents",
      url: "/documents",
      icon: HardDrive,
    },
    {
      name: "Shared",
      url: "/documents?filter=shared",
      icon: Users,
    },
    {
      name: "Starred",
      url: "/documents?filter=starred",
      icon: Star,
    },
    {
      name: "Recycle bin",
      url: "/documents?filter=trash",
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

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <Logo/>
      </SidebarHeader>
      <SidebarContent>
        <NavMain projects={data.main} />
        <NavLabels labels={labels} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  )
}
