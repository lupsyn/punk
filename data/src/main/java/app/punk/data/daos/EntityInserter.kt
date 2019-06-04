package app.punk.data.daos

import app.punk.data.DatabaseTransactionRunner
import app.punk.data.entities.BeerEntity


class EntityInserter constructor(
    private val transactionRunner: DatabaseTransactionRunner
) {
    fun <E : BeerEntity> insertOrUpdate(dao: BeerEntityDao<E>, entities: List<E>) = transactionRunner {
        entities.forEach {
            insertOrUpdate(dao, it)
        }
    }

    fun <E : BeerEntity> insertOrUpdate(dao: BeerEntityDao<E>, entity: E): Long {
//        logger.d("insertOrUpdate. entity: %s", entity)
        return when {
            entity.id == 0 -> dao.insert(entity)
            else -> {
                dao.update(entity)
                entity.id.toLong()
            }
        }
    }
}