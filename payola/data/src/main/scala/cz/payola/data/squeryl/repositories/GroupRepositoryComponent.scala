package cz.payola.data.squeryl.repositories

import cz.payola.data.squeryl.entities._
import org.squeryl.PrimitiveTypeMode._
import cz.payola.data.squeryl._
import cz.payola.data._
import org.squeryl.Query
import cz.payola.data.PaginationInfo
import org.squeryl.dsl.ast.LogicalBoolean

trait GroupRepositoryComponent extends TableRepositoryComponent
{
    self: SquerylDataContextComponent =>

    lazy val groupRepository = new TableRepository[Group, (Group, User, Option[User])](schema.groups, Group)
        with GroupRepository
    {
        def getAllByOwnerId(ownerId: String, pagination: Option[PaginationInfo] = None) = wrapInTransaction {
            // TODO pagination
            selectWhere(_.ownerId === ownerId)
        }

        protected def getSelectQuery(entityFilter: Group => LogicalBoolean) = {
            join(schema.groups, schema.users, schema.groupMembership.leftOuter, schema.users.leftOuter)((g, o, a, u) =>
                where(entityFilter(g))
                select(g, o, u)
                on(g.ownerId === o.id, Option(g.id) === a.map(_.groupId), a.map(_.memberId) === u.map(_.id))
            )
        }

        protected def processSelectResults(results: Seq[(Group, User, Option[User])]): Seq[Group] = {
            results.groupBy(_._1).map { r =>
                val group = r._1
                group.owner = r._2.head._2
                group.members = r._2.flatMap(_._3)
                group
            }(collection.breakOut)
        }
    }
}